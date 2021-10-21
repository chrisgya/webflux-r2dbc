package com.chrisgya.webfluxr2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Table("ecomm.payment")
public class PaymentEntity {

  @Id
  @Column("id")
  private UUID id;

  @Column("authorized")
  private boolean authorized;

  @Column("message")
  private String message;

  private OrderEntity orderEntity;

  public UUID getId() {
    return id;
  }

  public PaymentEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public PaymentEntity setAuthorized(boolean authorized) {
    this.authorized = authorized;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public PaymentEntity setMessage(String message) {
    this.message = message;
    return this;
  }
}
