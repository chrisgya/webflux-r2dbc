package com.chrisgya.webfluxr2dbc.service;

import com.chrisgya.webfluxr2dbc.entity.AddressEntity;
import com.chrisgya.webfluxr2dbc.entity.CardEntity;
import com.chrisgya.webfluxr2dbc.entity.UserEntity;
import com.chrisgya.webfluxr2dbc.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Void> deleteCustomerById(String id) {
    return deleteCustomerById(UUID.fromString(id));
  }

  @Override
  public Mono<Void> deleteCustomerById(UUID id) {
    return repository.deleteById(id).then();
  }

  @Override
  public Flux<AddressEntity> getAddressesByCustomerId(String id) {
    return repository.getAddressesByCustomerId(id);
  }

  @Override
  public Flux<UserEntity> getAllCustomers() {
    return repository.findAll();
  }

  @Override
  public Mono<CardEntity> getCardByCustomerId(String id) {
    return repository.findCardByCustomerId(id);
  }

  @Override
  public Mono<UserEntity> getCustomerById(String id) {
    return repository.findById(UUID.fromString(id));
  }
}
