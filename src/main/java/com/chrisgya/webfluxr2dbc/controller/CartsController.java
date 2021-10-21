package com.chrisgya.webfluxr2dbc.controller;


import com.chrisgya.webflux_r2dbc.CartApi;
import com.chrisgya.webflux_r2dbc.model.Cart;
import com.chrisgya.webflux_r2dbc.model.Item;
import com.chrisgya.webfluxr2dbc.hateoas.CartRepresentationModelAssembler;
import com.chrisgya.webfluxr2dbc.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class CartsController implements CartApi {

  private static final Logger log = LoggerFactory.getLogger(CartsController.class);
  private final CartRepresentationModelAssembler assembler;
  private CartService service;

  public CartsController(CartService service, CartRepresentationModelAssembler assembler) {
    this.service = service;
    this.assembler = assembler;
  }

  @Override
  public Mono<ResponseEntity<Flux<Item>>> addCartItemsByCustomerId(String customerId,
                                                                   @Valid Mono<Item> item, ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId)
        .map(
            a -> status(HttpStatus.CREATED).body(service.addCartItemsByCustomerId(a, item.cache())))
        .switchIfEmpty(Mono.just(notFound().build()));
  }

  @Override
  public Mono<ResponseEntity<Flux<Item>>> addOrReplaceItemsByCustomerId(String customerId,
      @Valid Mono<Item> item, ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId)
        .map(
            a -> status(HttpStatus.CREATED).body(service.addOrReplaceItemsByCustomerId(a, item.cache())))
        .switchIfEmpty(Mono.just(notFound().build()));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteCart(String customerId, ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId)
        .flatMap(a -> service.deleteCart(a.getUser().getId().toString(), a.getId().toString())
            .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
        .switchIfEmpty(Mono.just(notFound().build()));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteItemFromCart(String customerId, String itemId,
      ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId)
        .flatMap(a -> service.deleteItemFromCart(a, itemId.trim())
            .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
        .switchIfEmpty(Mono.just(notFound().build()));
  }

  @Override
  public Mono<ResponseEntity<Cart>> getCartByCustomerId(String customerId,
                                                        ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId).map(c -> assembler.entityToModel(c, exchange))
        .map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
  }

  @Override
  public Mono<ResponseEntity<Flux<Item>>> getCartItemsByCustomerId(String customerId,
      ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId)
        .map(a -> Flux.fromIterable(assembler.itemfromEntities(a.getItems())))
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(notFound().build()));
  }

  @Override
  public Mono<ResponseEntity<Item>> getCartItemsByItemId(String customerId, String itemId,
      ServerWebExchange exchange) {
    return service.getCartByCustomerId(customerId)
        .map(cart ->
            assembler.itemfromEntities(cart.getItems().stream()
                .filter(i -> i.getProductId().toString().equals(itemId.trim())).collect(toList()))
                .get(0)).map(ResponseEntity::ok)
        .onErrorReturn(notFound().build())
        .switchIfEmpty(Mono.just(notFound().build()));
  }
}
