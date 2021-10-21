package com.chrisgya.webfluxr2dbc.service;


import com.chrisgya.webflux_r2dbc.model.AddAddressReq;
import com.chrisgya.webfluxr2dbc.entity.AddressEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AddressService {
  Mono<AddressEntity> createAddress(Mono<AddAddressReq> addAddressReq);
  Mono<Void> deleteAddressesById(String id);
  Mono<Void> deleteAddressesById(UUID id);
  Mono<AddressEntity> getAddressesById(String id);
  Flux<AddressEntity> getAllAddresses();
}
