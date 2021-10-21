package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webfluxr2dbc.entity.AddressEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface AddressRepository extends ReactiveCrudRepository<AddressEntity, UUID> {
}

