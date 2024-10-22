package com.sep.backend_noAuth.service.integrationTest;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureDataMongo
public class AddressServiceIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        // Clean up the test DB before each test
        // mongoTemplate.dropCollection(Address.class);
    }

    @Test
    void getAddressListToValidate_ShouldReturnAddresses() {
        // Arrange
        Address address = new Address();
        address.setCity("Kochchikade");
        address.setHouseNumber("79");
        address.setZone("Pallansena South");

        mongoTemplate.save(address);

        AddressRequest request = new AddressRequest();
        request.setCity("Kochchikade");
        request.setHouseNumber("79");
        request.setZone("Pallansena South");

        // Act
        List<Address> result = addressService.getAddressListToValidate(request);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Kochchikade", result.get(0).getCity());
        mongoTemplate.remove(address);
    }

    @Test
    void updateCustomerAddress_ShouldUpdateAddressCorrectly() {
        // Arrange
        Address address = new Address();
        address.setAddressId("19");
        List<AddressMemberDto> members = new ArrayList<>();
        members.add(new AddressMemberDto("1","Tharindu"));
        members.add(new AddressMemberDto("2","Sankalpa"));

        address.setMembers(members);
        mongoTemplate.save(address);

        String addressId = "19";
        String customerIdToBeRemoved = "1";

        // Act
        Optional<Address> updatedAddress = addressService.updateCustomerAddress(addressId, customerIdToBeRemoved);

        // Assert
        assertTrue(updatedAddress.isPresent());
        assertEquals("2", updatedAddress.get().getMembers().get(0).getCustomerId());
        mongoTemplate.remove(address);
    }
}
