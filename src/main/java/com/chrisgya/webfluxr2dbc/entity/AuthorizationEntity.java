package com.chrisgya.webfluxr2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Table("ecomm.authorization")
public class AuthorizationEntity {

  @Id
  @Column("id")
  private UUID id;

  @Column("authorized")
  private boolean authorized;

  @Column("time")
  private Timestamp time;

  @Column("message")
  private String message;

  @Column("error")
  private String error;

  private OrderEntity orderEntity;

  public UUID getId() {
    return id;
  }

  public AuthorizationEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public AuthorizationEntity setAuthorized(boolean authorized) {
    this.authorized = authorized;
    return this;
  }

  public Timestamp getTime() {
    return time;
  }

  public AuthorizationEntity setTime(Timestamp time) {
    this.time = time;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public AuthorizationEntity setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getError() {
    return error;
  }

  public AuthorizationEntity setError(String error) {
    this.error = error;
    return this;
  }

  public OrderEntity getOrderEntity() {
    return orderEntity;
  }

  public AuthorizationEntity setOrderEntity(OrderEntity orderEntity) {
    this.orderEntity = orderEntity;
    return this;
  }
}
