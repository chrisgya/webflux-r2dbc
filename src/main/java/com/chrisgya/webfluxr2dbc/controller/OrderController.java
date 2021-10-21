package com.chrisgya.webfluxr2dbc.controller;


import com.chrisgya.webflux_r2dbc.OrderApi;
import com.chrisgya.webflux_r2dbc.model.NewOrder;
import com.chrisgya.webflux_r2dbc.model.Order;
import com.chrisgya.webfluxr2dbc.hateoas.OrderRepresentationModelAssembler;
import com.chrisgya.webfluxr2dbc.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class OrderController implements OrderApi {

  private final OrderRepresentationModelAssembler assembler;
  private OrderService service;

  public OrderController(OrderService service, OrderRepresentationModelAssembler assembler) {
    this.service = service;
    this.assembler = assembler;
  }

  @Override
  public Mono<ResponseEntity<Order>> addOrder(@Valid Mono<NewOrder> newOrder,
      ServerWebExchange exchange) {
    return service.addOrder(newOrder.cache())
        .zipWhen(x -> service.updateMapping(x))
        .map(t -> status(HttpStatus.CREATED).body(assembler.entityToModel(t.getT2(), exchange)))
        .defaultIfEmpty(notFound().build());
  }

  @Override
  public Mono<ResponseEntity<Flux<Order>>> getOrdersByCustomerId(@NotNull @Valid String customerId,
                                                                 ServerWebExchange exchange) {
    return Mono
        .just(ok(assembler.toListModel(service.getOrdersByCustomerId(customerId), exchange)));
  }

  @Override
  public Mono<ResponseEntity<Order>> getByOrderId(String id, ServerWebExchange exchange) {
    return service.getByOrderId(id).map(o -> assembler.entityToModel(o, exchange))
        .map(ResponseEntity::ok)
        .defaultIfEmpty(notFound().build());
  }
}
