package com.chrisgya.webfluxr2dbc.service;


import com.chrisgya.webflux_r2dbc.model.AddCardReq;
import com.chrisgya.webfluxr2dbc.entity.CardEntity;
import com.chrisgya.webfluxr2dbc.repository.CardRepository;
import com.chrisgya.webfluxr2dbc.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

  private CardRepository repository;
  private UserRepository userRepo;

  public CardServiceImpl(CardRepository repository, UserRepository userRepo) {
    this.repository = repository;
    this.userRepo = userRepo;
  }

  @Override
  public Mono<Void> deleteCardById(String id) {
    return deleteCardById(UUID.fromString(id));
  }

  @Override
  public Mono<Void> deleteCardById(UUID id) {
    return repository.deleteById(id);
  }

  @Override
  public Flux<CardEntity> getAllCards() {
    return repository.findAll();
  }

  @Override
  public Mono<CardEntity> getCardById(String id) {
    return repository.findById(UUID.fromString(id));
  }

  @Override
  public Mono<CardEntity> registerCard(@Valid Mono<AddCardReq> addCardReq) {
    return addCardReq.map(this::toEntity).flatMap(repository::save);
  }

  @Override
  public CardEntity toEntity(AddCardReq model) {
    CardEntity e = new CardEntity();
    BeanUtils.copyProperties(model, e);
    e.setNumber(model.getCardNumber());
    //Mono<UserEntity> user = userRepo.findById(UUID.fromString(model.getUserId()));
    //user.map(u -> e.setUser(u));
    return e;
  }
}
