package com.sep.backend_noAuth.controller.Postmaster;


import com.sep.backend_noAuth.entity.Payment;
import com.sep.backend_noAuth.repository.PaymentRepository;
import com.sep.backend_noAuth.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/postmaster/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/list/{mailType}")
    public List<Payment> getAllNormalPostPayments(@PathVariable String mailType){
        return paymentService.getPaymentDetailsByMailType(mailType);
    }
}
