package com.sep.backend_noAuth.service.integrationTest;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.Customer.ProfileUpdateRequestDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.CustomerRepository;
import com.sep.backend_noAuth.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    private Customer testCustomer;
    private Address testAddress;

    @BeforeEach
    void setUp() {
        // Setup initial data for integration tests
        testCustomer = new Customer();
        testCustomer.setId("C123");
        testCustomer.setFullName("John Doe");
        testCustomer.setAddressId("A001");
        customerRepository.save(testCustomer);

        testAddress = new Address();
        testAddress.setAddressId("A001");
        testAddress.setTextForm("1, Pallansena North, Negombo");
        testAddress.setMembers(Arrays.asList(new AddressMemberDto(testCustomer.getId(), testCustomer.getFullName())));
        addressRepository.save(testAddress);
    }

    @AfterEach
    void tearDown() {
        // Explicitly delete only the records inserted during the test
        addressRepository.delete(testAddress);
        customerRepository.delete(testCustomer);
    }

    @Test
    void updateCustomer_ShouldUpdateCustomerAndAddress_WhenCustomerExists() {
        ProfileUpdateRequestDto dto = new ProfileUpdateRequestDto();
        dto.setId("C123");
        dto.setAddress("1, Pallansena South, Negombo");
        dto.setContact("0711234567");

        Address address = new Address();
        address.setAddressId("adr232");
        address.setTextForm("2, Pallansena North, Negombo");
        List<AddressMemberDto> members = new ArrayList<>();
        AddressMemberDto member = new AddressMemberDto();
        member.setCustomerId("C123");
        member.setName("Kamal");
        members.add(member);
        address.setMembers(members);
        addressRepository.save(address);

        Customer customer = new Customer();
        customer.setId("C123");
        customer.setAddressId("adr232");
        customer.setContactNumber("0711234567");
        customerRepository.save(customer);

        Optional<Customer> updatedCustomer = customerService.updateCustomer(dto);

        assertTrue(updatedCustomer.isPresent());
        assertEquals(dto.getContact(), updatedCustomer.get().getContactNumber());

        // Verify the address has been updated in the database
        Optional<Address> updatedAddress = addressRepository.findById(address.getAddressId());
        assertTrue(updatedAddress.isPresent());
        assertEquals("2, Pallansena North, Negombo", updatedAddress.get().getTextForm());

        addressRepository.delete(address);
        customerRepository.delete(customer);
    }

    @Test
    void updateCustomer_ShouldReturnEmpty_WhenCustomerDoesNotExist() {
        ProfileUpdateRequestDto dto = new ProfileUpdateRequestDto();
        dto.setId("C999"); // Non-existing customer ID

        Optional<Customer> result = customerService.updateCustomer(dto);

        assertFalse(result.isPresent());
        // Verify that no address save operation is performed
        assertEquals(31, customerRepository.count());
    }

    @Test
    void getCustomerInfo_ShouldReturnCustomer_WhenCustomerExists() {
        Customer customer = customerService.getCustomerInfo(testCustomer.getId());

        assertNotNull(customer);
        assertEquals(testCustomer.getId(), customer.getId());
    }

    @Test
    void extractCityAndZone_ShouldReturnCorrectArray_WhenAddressIsValid() {
        String address = "1, Pallansena, Negombo";

        String[] expected = {"1", "Pallansena", "Negombo"};
        String[] result = customerService.extractCityAndZone(address);

        assertArrayEquals(expected, result);
    }

    @Test
    void extractCityAndZone_ShouldReturnEmptyArray_WhenAddressIsInvalid() {
        String address = "";

        String[] result = customerService.extractCityAndZone(address);

        assertEquals(0, result.length);
    }
}
