package com.chrisgya.webfluxr2dbc.service;

import com.chrisgya.webfluxr2dbc.entity.AddressEntity;
import com.chrisgya.webfluxr2dbc.entity.CardEntity;
import com.chrisgya.webfluxr2dbc.entity.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
  Mono<Void> deleteCustomerById(String id);
  Mono<Void> deleteCustomerById(UUID id);
  Flux<AddressEntity> getAddressesByCustomerId(String id);
  Flux<UserEntity> getAllCustomers();
  Mono<CardEntity> getCardByCustomerId(String id);
  Mono<UserEntity> getCustomerById(String id);
}
