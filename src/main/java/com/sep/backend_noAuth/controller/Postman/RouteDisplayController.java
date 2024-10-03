package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("api/postman/route-display")
public class RouteDisplayController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/get-delivery")
    public Delivery getDelivery(@RequestParam String postmanId){

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        return deliveryRepository.findByDateAndPostmanId(date,postmanId);
    }

}
