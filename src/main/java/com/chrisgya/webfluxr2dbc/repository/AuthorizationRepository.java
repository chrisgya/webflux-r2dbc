package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webfluxr2dbc.entity.AuthorizationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;


public interface AuthorizationRepository extends ReactiveCrudRepository<AuthorizationEntity, UUID> {
}

