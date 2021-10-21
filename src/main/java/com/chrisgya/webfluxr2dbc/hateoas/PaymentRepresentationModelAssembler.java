package com.chrisgya.webfluxr2dbc.hateoas;


import com.chrisgya.webflux_r2dbc.model.Payment;
import com.chrisgya.webfluxr2dbc.entity.PaymentEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PaymentRepresentationModelAssembler implements
        ReactiveRepresentationModelAssembler<PaymentEntity, Payment> {

  /**
   * Coverts the Payment entity to resource
   * @param entity
   * @return
   */
  @Override
  public Mono<Payment> toModel(PaymentEntity entity, ServerWebExchange exchange) {
    return Mono.just(entityToModel(entity));
  }

  public Payment entityToModel(PaymentEntity entity) {
    Payment resource = new Payment();
    if(Objects.isNull(entity)) {
      return resource;
    }
    BeanUtils.copyProperties(entity, resource);
    resource.add(Link.of("/api/v1/Payments").withRel("Payments"));
    resource.add(Link.of(String.format("/api/v1/Payments/%s", entity.getId())).withRel("self"));
    return resource;
  }

  public Payment getModel(Mono<Payment> m) {
    AtomicReference<Payment> model = new AtomicReference<>();
    m.cache().subscribe(i -> model.set(i));
    return model.get();
  }

  /**
   * Coverts the collection of Product entities to list of resources.
   *
   * @param entities
   */
  public Flux<Payment> toListModel(Flux<PaymentEntity> entities) {
    if (Objects.isNull(entities)) {
      return Flux.empty();
    }
    return Flux.from(entities.map(e -> entityToModel(e)));
  }

}
