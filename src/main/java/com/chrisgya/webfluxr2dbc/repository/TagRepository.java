package com.chrisgya.webfluxr2dbc.repository;

import com.chrisgya.webfluxr2dbc.entity.TagEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface TagRepository extends ReactiveCrudRepository<TagEntity, UUID> {

    @Query("""
            SELECT t.* FROM ecomm.product p, ecomm.tag t, ecomm.product_tag pt 
            where p.id = :id and p.id=pt.product_id and t.id = pt.tag_id
            """)
    Flux<TagEntity> findTagsByProductId(String id);
}
