package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.dto.PaymentGatewayOrderDto;
import com.sep.backend_noAuth.dto.PaymentGatewayResponseDto;
import com.sep.backend_noAuth.service.PaymentGatewayCustomerServiceImpl;
import com.sep.backend_noAuth.service.PaymentGatewayService;
import com.sep.backend_noAuth.service.PaymentGatewayServiceImpl;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-gateway")
public class PaymentGatewayController {

    private final PaymentGatewayService paymentGatewayService;
    private final PaymentGatewayService paymentGatewayCustomerService;

    @Autowired
    public PaymentGatewayController(PaymentGatewayServiceImpl paymentGatewayServiceImpl, PaymentGatewayCustomerServiceImpl paymentGatewayCustomerServiceImpl) {
        this.paymentGatewayService = paymentGatewayServiceImpl;
        this.paymentGatewayCustomerService = paymentGatewayCustomerServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentGatewayResponseDto> createOrder() throws StripeException {
        PaymentGatewayOrderDto order = new PaymentGatewayOrderDto();
        order.setOrderId("1");
        order.setCustomerId("20");
        order.setTotalAmount(4900L);
        PaymentGatewayResponseDto response = paymentGatewayService.createPaymentLink(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/customer/create")
    public ResponseEntity<PaymentGatewayResponseDto> createOrderCustomer() throws StripeException {
        PaymentGatewayOrderDto order = new PaymentGatewayOrderDto();
        order.setOrderId("1");
        order.setCustomerId("20");
        order.setTotalAmount(9900L);
        PaymentGatewayResponseDto response = paymentGatewayCustomerService.createPaymentLink(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
