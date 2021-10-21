package com.chrisgya.webfluxr2dbc.service;


import com.chrisgya.webfluxr2dbc.entity.ProductEntity;
import com.chrisgya.webfluxr2dbc.entity.TagEntity;
import com.chrisgya.webfluxr2dbc.repository.ProductRepository;
import com.chrisgya.webfluxr2dbc.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

  private ProductRepository repository;
  private TagRepository tagRepo;
  private BiFunction<ProductEntity, List<TagEntity>, ProductEntity> productTagBiFun = (p, t) -> p.setTags(t);

  public ProductServiceImpl(ProductRepository repository, TagRepository tagRepo) {
    this.repository = repository;
    this.tagRepo = tagRepo;
  }

  @Override
  public Flux<ProductEntity> getAllProducts() {
    return repository.findAll()
        .flatMap(products ->
            Mono.just(products)
                .zipWith(tagRepo.findTagsByProductId(products.getId().toString()).collectList())
                .map(t -> t.getT1().setTags(t.getT2()))
        );
  }

  @Override
  public Mono<ProductEntity> getProduct(String id) {
    Mono<ProductEntity> product = repository.findById(UUID.fromString(id))
        .subscribeOn(Schedulers.boundedElastic());
    Flux<TagEntity> tags = tagRepo.findTagsByProductId(id).subscribeOn(Schedulers.boundedElastic());
    return Mono.zip(product, tags.collectList(), productTagBiFun);
  }
}
