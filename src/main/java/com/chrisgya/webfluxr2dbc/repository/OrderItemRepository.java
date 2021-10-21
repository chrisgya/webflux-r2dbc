package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webfluxr2dbc.entity.OrderItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItemEntity, UUID> {

}
