package com.chrisgya.webfluxr2dbc.hateoas;

import com.chrisgya.webflux_r2dbc.model.Shipment;
import com.chrisgya.webfluxr2dbc.entity.ShipmentEntity;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Component
public class ShipmentRepresentationModelAssembler implements
    ReactiveRepresentationModelAssembler<ShipmentEntity, Shipment>, HateoasSupport {

  private static String serverUri = null;

  private String getServerUri(@Nullable ServerWebExchange exchange) {
    if (Strings.isBlank(serverUri)) {
      serverUri = getUriComponentBuilder(exchange).toUriString();
    }
    return serverUri;
  }

  /**
   * Coverts the Shipment entity to resource
   * @param entity
   * @return
   */
  @Override
  public Mono<Shipment> toModel(ShipmentEntity entity, ServerWebExchange exchange) {
    return Mono.just(entityToModel(entity, exchange));
  }

  public Shipment entityToModel(ShipmentEntity entity, ServerWebExchange exchange) {
    Shipment resource = new Shipment();
    if(Objects.isNull(entity)) {
      return resource;
    }
    BeanUtils.copyProperties(entity, resource);
    String serverUri = getServerUri(exchange);
    resource.add(Link.of(String.format("%s/api/v1/shipping/%s", serverUri, entity.getId())).withRel("self"));
    return resource;
  }

  /**
   * Coverts the collection of Product entities to list of resources.
   *
   * @param entities
   */
  public Flux<Shipment> toListModel(Flux<ShipmentEntity> entities, ServerWebExchange exchange) {
    if (Objects.isNull(entities)) {
      return Flux.empty();
    }
    return Flux.from(entities.map(e -> entityToModel(e, exchange)));
  }
}
