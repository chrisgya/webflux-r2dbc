package com.chrisgya.webfluxr2dbc.service;


import com.chrisgya.webfluxr2dbc.entity.ShipmentEntity;
import reactor.core.publisher.Flux;

import javax.validation.constraints.Min;

public interface ShipmentService {
  Flux<ShipmentEntity> getShipmentByOrderId(@Min(value = 1L, message = "Invalid product ID.")  String id);
}
