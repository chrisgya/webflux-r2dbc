package com.chrisgya.webfluxr2dbc.service;


import com.chrisgya.webflux_r2dbc.model.PaymentReq;
import com.chrisgya.webfluxr2dbc.entity.AuthorizationEntity;
import com.chrisgya.webfluxr2dbc.repository.OrderRepository;
import com.chrisgya.webfluxr2dbc.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

  private PaymentRepository repository;
  private OrderRepository orderRepo;

  public PaymentServiceImpl(PaymentRepository repository, OrderRepository orderRepo) {
    this.repository = repository;
    this.orderRepo = orderRepo;
  }

  @Override
  public Mono<AuthorizationEntity> authorize(@Valid Mono<PaymentReq> paymentReq) {
    return Mono.empty();
  }

  @Override
  public Mono<AuthorizationEntity> getOrdersPaymentAuthorization(@NotNull String orderId) {
    return orderRepo.findById(UUID.fromString(orderId)).map(oe -> oe.getAuthorizationEntity());
  }

  /*private AuthorizationEntity toEntity(PaymentReq m) {
    PaymentEntity e = new PaymentEntity();
    e.setAuthorized(true).setMessage()
  }*/
}
