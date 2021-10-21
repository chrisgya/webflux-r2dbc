package com.chrisgya.webfluxr2dbc.repository;


import com.chrisgya.webfluxr2dbc.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, UUID> {

}
