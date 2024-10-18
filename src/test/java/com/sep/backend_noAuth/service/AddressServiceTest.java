package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.repository.AddressRepository;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAddressListToValidate_ShouldReturnAddresses() {
        // Arrange
        AddressRequest request = new AddressRequest();
        request.setCity("Kochchikade");
        request.setHouseNumber("79");
        request.setZone("Pallansena South");

        Address address = new Address();
        // Set properties on the address object as needed

        when(mongoTemplate.find(any(Query.class), eq(Address.class))).thenReturn(Collections.singletonList(address));

        // Act
        List<Address> addresses = addressService.getAddressListToValidate(request);

        // Assert
        assertNotNull(addresses);
        assertEquals(1, addresses.size());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Address.class));
    }


    @Test
    void updateCustomerAddress_ShouldHandleAddressNotFound() {
        // Arrange
        String addressId = "19";
        String customerId = "1";

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        // Act
        Optional<Address> updatedAddress = addressService.updateCustomerAddress(addressId, customerId);
        if(updatedAddress!=null){
            System.out.println("ss");
            assertFalse(updatedAddress.isPresent());
        }
        verify(addressRepository, times(1)).findById(addressId);
    }
}
