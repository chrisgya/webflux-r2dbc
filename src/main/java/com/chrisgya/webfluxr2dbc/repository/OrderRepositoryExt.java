package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webflux_r2dbc.model.NewOrder;
import com.chrisgya.webfluxr2dbc.entity.OrderEntity;
import reactor.core.publisher.Mono;


public interface OrderRepositoryExt {

  Mono<OrderEntity> insert(Mono<NewOrder> m);

  Mono<OrderEntity> updateMapping(OrderEntity orderEntity);
}

