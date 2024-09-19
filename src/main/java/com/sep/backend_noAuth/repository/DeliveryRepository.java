package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeliveryRepository extends MongoRepository<Delivery,String> {
    Delivery findByDeliveryId(String deliveryId);
    Delivery findByDateAndPostmanId(String date,String postmanId);
}
