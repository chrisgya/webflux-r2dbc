package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webfluxr2dbc.entity.CardEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CardRepository extends ReactiveCrudRepository<CardEntity, UUID> {

}

