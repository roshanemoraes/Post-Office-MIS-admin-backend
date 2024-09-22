package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.PaymentGatewayOrderDto;
import com.sep.backend_noAuth.dto.PaymentGatewayResponseDto;
import com.stripe.exception.StripeException;

public interface PaymentGatewayService {
    public PaymentGatewayResponseDto createPaymentLink(PaymentGatewayOrderDto order) throws StripeException;
}
