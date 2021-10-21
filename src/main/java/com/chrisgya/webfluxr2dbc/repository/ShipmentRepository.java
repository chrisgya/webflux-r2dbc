package com.chrisgya.webfluxr2dbc.repository;


import com.chrisgya.webfluxr2dbc.entity.ShipmentEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ShipmentRepository extends ReactiveCrudRepository<ShipmentEntity, UUID> {

  @Query("SELECT s.* FROM ecomm.order o, ecomm.shipment s where o.shipment_id=s.id and o.id = :id")
  Flux<ShipmentEntity> getShipmentByOrderId(String id);
}

