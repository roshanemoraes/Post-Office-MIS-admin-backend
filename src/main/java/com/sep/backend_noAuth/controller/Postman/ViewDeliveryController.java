package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/postman/view-delivery")
public class ViewDeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/get-today-delivery/{postmanId}")
    public Delivery getTodayDelivery(@PathVariable String postmanId){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);
        return deliveryRepository.findByDateAndPostmanId(date, postmanId);
    }
}
