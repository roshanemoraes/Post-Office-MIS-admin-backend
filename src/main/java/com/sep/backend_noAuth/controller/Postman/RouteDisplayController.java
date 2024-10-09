package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Delivery> getDelivery(@RequestParam String postmanId){

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        Delivery delivery =deliveryRepository.findByDateAndPostmanId(date,postmanId);
        if (delivery == null){
            System.out.println();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(delivery);

    }

}
