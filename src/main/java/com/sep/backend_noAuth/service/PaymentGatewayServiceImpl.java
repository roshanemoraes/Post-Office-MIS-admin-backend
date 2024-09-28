package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.PaymentGatewayOrderDto;
import com.sep.backend_noAuth.dto.PaymentGatewayResponseDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService{

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;
    @Override
    public PaymentGatewayResponseDto createPaymentLink(PaymentGatewayOrderDto order) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8081/payment/success")
                .setCancelUrl("http://localhost:8081/payment/fail")
                .addLineItem(SessionCreateParams
                        .LineItem
                        .builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(order.getTotalAmount())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName("Money Order").build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);

        PaymentGatewayResponseDto response = new PaymentGatewayResponseDto();
        response.setPaymentUrl(session.getUrl());
        return response;
    }
}
