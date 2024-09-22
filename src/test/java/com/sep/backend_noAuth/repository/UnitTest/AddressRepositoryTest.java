package com.sep.backend_noAuth.repository.UnitTest;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the repository before each test
        addressRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindAddress() {
        // Given
        AddressMemberDto member1 = new AddressMemberDto("20","John");
        AddressMemberDto member2 = new AddressMemberDto("21","Jane");
        List<AddressMemberDto> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        Address address = Address.builder()
                .addressId("2032")
                .textForm("123 Main St, Test City, Test Zone")
                .lat("12.3456")
                .lng("98.7654")
                .city("Test City")
                .zone("Test Zone")
                .houseNumber("123")
                .members(members)
                .build();

        // When
        addressRepository.save(address);
        Address savedAddress = addressRepository.findByAddressId("2032");

        // Then
        assertNotNull(savedAddress);
        assertEquals("123 Main St, Test City, Test Zone", savedAddress.getTextForm());
        assertEquals("Test City", savedAddress.getCity());
        assertEquals(2, savedAddress.getMembers().size());
        assertEquals("John", savedAddress.getMembers().get(0).getName());
        assertEquals("Jane", savedAddress.getMembers().get(1).getName());
    }

    @Test
    public void testFindNonExistingAddress() {
        // When
        Address address = addressRepository.findByAddressId("non-existent-id");

        // Then
        assertNull(address);
    }

    @Test
    public void testDeleteAddress() {
        // Given
        Address address = Address.builder()
                .addressId("addr124")
                .textForm("456 Test Lane, Sample City, Sample Zone")
                .lat("11.1111")
                .lng("99.9999")
                .city("Sample City")
                .zone("Sample Zone")
                .houseNumber("456")
                .build();

        addressRepository.save(address);

        // When
        addressRepository.deleteById("addr124");

        // Then
        assertNull(addressRepository.findByAddressId("addr124"));
    }

    @Test
    public void testUpdateAddress() {
        // Given
        Address address = Address.builder()
                .addressId("addr125")
                .textForm("789 Another St, Another City, Another Zone")
                .lat("22.2222")
                .lng("88.8888")
                .city("Another City")
                .zone("Another Zone")
                .houseNumber("789")
                .build();

        addressRepository.save(address);

        // When
        Address savedAddress = addressRepository.findByAddressId("addr125");
        savedAddress.setCity("Updated City");
        addressRepository.save(savedAddress);

        Address updatedAddress = addressRepository.findByAddressId("addr125");

        // Then
        assertNotNull(updatedAddress);
        assertEquals("Updated City", updatedAddress.getCity());
    }
}
