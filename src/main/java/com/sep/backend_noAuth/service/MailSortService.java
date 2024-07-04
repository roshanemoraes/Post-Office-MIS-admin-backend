package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.DestinationDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MailSortService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private MailService mailService;

    public void createDeliveryObject(String zone, int postmanId){
        List<Mail> mailList = mailService.getAllPendingInAreaMailsForZone(zone);
        int listLength = mailList.size();
        Delivery delivery = new Delivery();
        delivery.setDeliveryId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Delivery.SEQUENCE_NAME)));
        delivery.setPostmanId(String.valueOf(postmanId));
        delivery.setDate(new Date().toInstant().toString());
        delivery.setVisitOrder("");
        delivery.setZone(zone);
        delivery.setStatus("Assigned");
        delivery.setDeliveredCount(listLength);   //TODO: here, i have set the delivered count as the total destination count. we will fetch this data during useEffect, save it to a variable and then make this initially 0;

        List<DestinationDto> destinations = new ArrayList<>();

        for(int i=0; i<listLength; i++){
           Mail mail = mailList.get(i);
           DestinationDto destination = new DestinationDto();
           destination.setAddressId(mail.getAddressId());

           Address address = addressRepository.findByAddressId(mail.getAddressId());
           destination.setLat(Double.parseDouble(address.getLat()));
           destination.setLng(Double.parseDouble(address.getLng()));

           destinations.add(destination);
        }
        delivery.setDestinations(destinations);
        deliveryRepository.save(delivery);
    }
}
