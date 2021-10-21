package com.chrisgya.webfluxr2dbc.service;

import com.chrisgya.webflux_r2dbc.model.PaymentReq;
import com.chrisgya.webfluxr2dbc.entity.AuthorizationEntity;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentService {

  Mono<AuthorizationEntity> authorize(@Valid Mono<PaymentReq> paymentReq);
  Mono<AuthorizationEntity> getOrdersPaymentAuthorization(@NotNull String orderId);
}
