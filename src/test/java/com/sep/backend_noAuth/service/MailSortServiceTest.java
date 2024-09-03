package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.DestinationDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MailSortServiceTest {
    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private MailSortService mailSortService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDeliveryObject() {
        String zone = "Pallansena";
        int postmanId = 2;
        List<Mail> mailList = new ArrayList<>();

        Mail mail = new NormalPost();
        mail.setMailId("M123");
        mail.setMailType("Registered");
        mail.setCustomerId("456");
        mail.setStatus("pending");
        mail.setDestinationAddress("1,abc,def");
        mail.setRecipientId("100");
        mail.setRecipientName("Peter");
        mail.setDatePosted("2024-08-25");
        mail.setDateDelivered("");
        mail.setZone("Pallansena");
        mail.setCity("pqr");
        mail.setAddressId("A222");
        mail.setIn_area(false);

        mailList.add(mail);

        Address address = new Address();
        address.setAddressId("A222");
        address.setLat("1.234");
        address.setLng("2.345");

        when(mailService.getAllPendingInAreaMailsForZone(zone)).thenReturn(mailList);
        when(sequenceGeneratorService.getSequenceNumber(Delivery.SEQUENCE_NAME)).thenReturn(1);
        when(addressRepository.findByAddressId("A222")).thenReturn(address);

        // When
        mailSortService.createDeliveryObject(zone, postmanId);

        // Then
        ArgumentCaptor<Delivery> deliveryCaptor = ArgumentCaptor.forClass(Delivery.class);
        verify(deliveryRepository, times(1)).save(deliveryCaptor.capture());

        Delivery savedDelivery = deliveryCaptor.getValue();
        assertEquals("1",savedDelivery.getDeliveryId());
        assertEquals("2",savedDelivery.getPostmanId());
        assertEquals(LocalDate.now().format(DateTimeFormatter.ISO_DATE), savedDelivery.getDate());
        assertEquals("Assigned", savedDelivery.getStatus());
        assertEquals(1, savedDelivery.getDeliveredCount());
        assertEquals(1, savedDelivery.getDestinations().size());

        DestinationDto destination = savedDelivery.getDestinations().get(0);
        assertEquals("A222", destination.getAddressId());
        assertEquals("M123", destination.getMailId());
        assertEquals(1.234, destination.getLat());
        assertEquals(2.345, destination.getLng());
    }

    @Test
    void findDeliveryForDateAndPostmanId() {
        String date = "2024-07-15";
        Long postmanId = 20L;

        Delivery mockDelivery = new Delivery();
        when(mongoTemplate.findOne(any(Query.class), eq(Delivery.class))).thenReturn(mockDelivery);

        //when
        Delivery delivery = mailSortService.findDeliveryForDateAndPostmanId(date, postmanId);

        //then
        assertNotNull(delivery);
        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Delivery.class));
    }


}