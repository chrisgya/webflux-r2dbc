package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webfluxr2dbc.entity.CartEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CartRepository extends ReactiveCrudRepository<CartEntity, UUID> {

  @Query("select c.* from ecomm.cart c join ecomm.user u on c.user_id=u.id where u.id = :customerId")
  Mono<CartEntity> findByCustomerId(String customerId);
}
