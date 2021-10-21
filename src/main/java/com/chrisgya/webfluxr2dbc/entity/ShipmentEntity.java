package com.chrisgya.webfluxr2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;


@Table("ecomm.shipment")
public class ShipmentEntity {
  @Id
  @Column("id")
  private UUID id;

  @Column("est_delivery_date")
  private Timestamp estDeliveryDate;

  @Column("carrier")
  private String carrier;

  public UUID getId() {
    return id;
  }

  public ShipmentEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public Timestamp getEstDeliveryDate() {
    return estDeliveryDate;
  }

  public ShipmentEntity setEstDeliveryDate(Timestamp estDeliveryDate) {
    this.estDeliveryDate = estDeliveryDate;
    return this;
  }

  public String getCarrier() {
    return carrier;
  }

  public ShipmentEntity setCarrier(String carrier) {
    this.carrier = carrier;
    return this;
  }

}
