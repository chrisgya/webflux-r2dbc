package com.chrisgya.webfluxr2dbc.hateoas;

import com.chrisgya.webflux_r2dbc.model.Product;
import com.chrisgya.webflux_r2dbc.model.Tag;
import com.chrisgya.webfluxr2dbc.entity.ProductEntity;
import com.chrisgya.webfluxr2dbc.entity.TagEntity;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
public class ProductRepresentationModelAssembler implements
    ReactiveRepresentationModelAssembler<ProductEntity, Product>, HateoasSupport {

  private static String serverUri = null;

  private String getServerUri(@Nullable ServerWebExchange exchange) {
    if (Strings.isBlank(serverUri)) {
      serverUri = getUriComponentBuilder(exchange).toUriString();
    }
    return serverUri;
  }

  /**
   * Coverts the Product entity to resource
   *
   * @param entity
   */
  @Override
  public Mono<Product> toModel(ProductEntity entity, ServerWebExchange exchange) {
    return Mono.just(entityToModel(entity, exchange));
  }

  public Product entityToModel(ProductEntity entity, ServerWebExchange exchange) {
    Product resource = new Product();
    if(Objects.isNull(entity)) {
      return resource;
    }
    BeanUtils.copyProperties(entity, resource);
    resource.setId(entity.getId().toString());
    resource.setTag(tagFromEntities(entity.getTags()));
    resource.add(Link.of("/api/v1/products").withRel("products"));
    resource.add(Link.of(String.format("/api/v1/Products/%s", entity.getId())).withSelfRel());
    return resource;
  }

  public List<Tag> tagFromEntities(List<TagEntity> tags) {
    return tags.stream().map(t -> {
      Tag tag = new Tag();
      BeanUtils.copyProperties(t, tag);
      tag.setId(t.getId().toString());
      return tag;
    }).collect(toList());
  }

  /**
   * Coverts the collection of Product entities to list of resources.
   *
   * @param entities
   */
  public Flux<Product> toListModel(Flux<ProductEntity> entities, ServerWebExchange exchange) {
    if (Objects.isNull(entities)) {
      return Flux.empty();
    }
    return Flux.from(entities.map(e -> entityToModel(e, exchange)));
  }
}
