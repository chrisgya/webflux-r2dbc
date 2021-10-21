package com.chrisgya.webfluxr2dbc.controller;

import com.chrisgya.webflux_r2dbc.CardApi;
import com.chrisgya.webflux_r2dbc.model.AddCardReq;
import com.chrisgya.webflux_r2dbc.model.Card;
import com.chrisgya.webfluxr2dbc.entity.UserEntity;
import com.chrisgya.webfluxr2dbc.exception.CardAlreadyExistsException;
import com.chrisgya.webfluxr2dbc.exception.CustomerNotFoundException;
import com.chrisgya.webfluxr2dbc.hateoas.CardRepresentationModelAssembler;
import com.chrisgya.webfluxr2dbc.service.CardService;
import com.chrisgya.webfluxr2dbc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.*;


@RestController
public class CardController implements CardApi {

  private final CardRepresentationModelAssembler assembler;
  private CardService service;
  private UserService userService;

  public CardController(CardService service, CardRepresentationModelAssembler assembler,
      UserService userService) {
    this.service = service;
    this.assembler = assembler;
    this.userService = userService;
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteCardById(String id, ServerWebExchange exchange) {
    return service.getCardById(id)
        .flatMap(c -> service.deleteCardById(c.getId())
            .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
        .switchIfEmpty(Mono.just(notFound().build()));
  }

  @Override
  public Mono<ResponseEntity<Card>> registerCard(@Valid Mono<AddCardReq> addCardReq,
                                                 ServerWebExchange exchange) {
    Mono<AddCardReq> mono = addCardReq.cache();
    return validate(mono)
        .flatMap(d -> userService.getCardByCustomerId(d.getId().toString())
            .flatMap(card -> {
              if (Objects.isNull(card.getId())) {
                return service.registerCard(mono)
                    .map(ce -> status(HttpStatus.CREATED)
                        .body(assembler.entityToModel(ce, exchange)));
              } else {
                return Mono.error(
                    () -> new CardAlreadyExistsException(" for user with ID - " + d.getId()));
              }
            })
            .switchIfEmpty(service.registerCard(mono)
                .map(ce -> status(HttpStatus.CREATED).body(assembler.entityToModel(ce, exchange))))
        );
  }

  private Mono<UserEntity> validate(Mono<AddCardReq> addCardReq) {
    return addCardReq.flatMap(req -> userService.getCustomerById(req.getUserId()))
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(". Check the payload.")));
  }


  @Override
  public Mono<ResponseEntity<Flux<Card>>> getAllCards(ServerWebExchange exchange) {
    return Mono.just(ok(assembler.toListModel(service.getAllCards(), exchange)));
  }

  @Override
  public Mono<ResponseEntity<Card>> getCardById(String id, ServerWebExchange exchange) {
    return service.getCardById(id).map(c -> assembler.entityToModel(c, exchange))
        .map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
  }
}
