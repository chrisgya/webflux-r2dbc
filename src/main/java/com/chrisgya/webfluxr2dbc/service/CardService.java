package com.chrisgya.webfluxr2dbc.service;


import com.chrisgya.webflux_r2dbc.model.AddCardReq;
import com.chrisgya.webfluxr2dbc.entity.CardEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

public interface CardService {
  Mono<Void> deleteCardById(String id);
  Mono<Void> deleteCardById(UUID id);
  Flux<CardEntity> getAllCards();
  Mono<CardEntity> getCardById(String id);
  Mono<CardEntity> registerCard(@Valid Mono<AddCardReq> addCardReq);
  CardEntity toEntity(AddCardReq model);
}
