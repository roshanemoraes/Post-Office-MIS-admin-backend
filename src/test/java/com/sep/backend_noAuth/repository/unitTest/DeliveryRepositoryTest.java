package com.sep.backend_noAuth.repository.unitTest;

import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.dto.DestinationDto;
import com.sep.backend_noAuth.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataMongoTest
@SpringBootTest
@ActiveProfiles("test")
public class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the repository before each test
        //deliveryRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindDelivery() {
        // Given
        DestinationDto destination1 = DestinationDto.builder()
                .addressId("addr123")
                .mailId("mail123")
                .lat(12.3456)
                .lng(98.7654)
                .build();

        DestinationDto destination2 = DestinationDto.builder()
                .addressId("addr124")
                .mailId("mail124")
                .lat(22.2222)
                .lng(88.8888)
                .build();

        List<DestinationDto> destinations = new ArrayList<>();
        destinations.add(destination1);
        destinations.add(destination2);

        Delivery delivery = Delivery.builder()
                .deliveryId("delivery123")
                .postmanId("postman1")
                .date("2023-09-22")
                .destinations(destinations)
                .visitOrder("1")
                .zone("Zone 1")
                .status("In Progress")
                .deliveredCount(0)
                .build();

        // When
        deliveryRepository.save(delivery);
        Delivery savedDelivery = deliveryRepository.findByDeliveryId("delivery123");

        // Then
        assertNotNull(savedDelivery);
        assertEquals("postman1", savedDelivery.getPostmanId());
        assertEquals("2023-09-22", savedDelivery.getDate());
        assertEquals(2, savedDelivery.getDestinations().size());
        assertEquals("addr123", savedDelivery.getDestinations().get(0).getAddressId());
        assertEquals("mail123", savedDelivery.getDestinations().get(0).getMailId());
    }

    @Test
    public void testFindByDateAndPostmanId() {
        // Given
        DestinationDto destination = DestinationDto.builder()
                .addressId("addr125")
                .mailId("mail125")
                .lat(33.3333)
                .lng(77.7777)
                .build();

        List<DestinationDto> destinations = new ArrayList<>();
        destinations.add(destination);

        Delivery delivery = Delivery.builder()
                .deliveryId("delivery124")
                .postmanId("postman2")
                .date("2023-09-23")
                .destinations(destinations)
                .visitOrder("2")
                .zone("Zone 2")
                .status("Delivered")
                .deliveredCount(1)
                .build();

        deliveryRepository.save(delivery);

        // When
        Delivery retrievedDelivery = deliveryRepository.findByDateAndPostmanId("2023-09-23", "postman2");

        // Then
        assertNotNull(retrievedDelivery);
        assertEquals("postman2", retrievedDelivery.getPostmanId());
        assertEquals("2023-09-23", retrievedDelivery.getDate());
        assertEquals(1, retrievedDelivery.getDeliveredCount());
    }

    @Test
    public void testDeleteDelivery() {
        // Given
        Delivery delivery = Delivery.builder()
                .deliveryId("delivery125")
                .postmanId("postman3")
                .date("2023-09-24")
                .visitOrder("3")
                .zone("Zone 3")
                .status("Pending")
                .deliveredCount(0)
                .build();

        deliveryRepository.save(delivery);

        // When
        deliveryRepository.deleteById("delivery125");

        // Then
        assertNull(deliveryRepository.findByDeliveryId("delivery125"));
    }

    @Test
    public void testUpdateDeliveryStatus() {
        // Given
        Delivery delivery = Delivery.builder()
                .deliveryId("delivery126")
                .postmanId("postman4")
                .date("2023-09-25")
                .visitOrder("4")
                .zone("Zone 4")
                .status("In Progress")
                .deliveredCount(0)
                .build();

        deliveryRepository.save(delivery);

        // When
        Delivery savedDelivery = deliveryRepository.findByDeliveryId("delivery126");
        savedDelivery.setStatus("Delivered");
        savedDelivery.setDeliveredCount(5);
        deliveryRepository.save(savedDelivery);

        Delivery updatedDelivery = deliveryRepository.findByDeliveryId("delivery126");

        // Then
        assertNotNull(updatedDelivery);
        assertEquals("Delivered", updatedDelivery.getStatus());
        assertEquals(5, updatedDelivery.getDeliveredCount());
    }
}