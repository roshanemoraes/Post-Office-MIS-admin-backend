package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.Customer.ProfileUpdateRequestDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCustomerInfo_ShouldReturnCustomer_WhenCustomerExists() {
        String customerId = "C123";
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);

        when(mongoTemplate.findOne(any(Query.class), eq(Customer.class))).thenReturn(mockCustomer);

        Customer customer = customerService.getCustomerInfo(customerId);

        assertNotNull(customer);
        assertEquals(customerId, customer.getId());
        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Customer.class));
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

    @Test
    void updateCustomer_ShouldUpdateCustomerAndAddress_WhenCustomerExists() {
        ProfileUpdateRequestDto dto = new ProfileUpdateRequestDto();
        dto.setId("C123");
        dto.setAddress("1, Pallansena, Negombo");
        dto.setContact("0711234567");

        Customer mockCustomer = new Customer();
        mockCustomer.setId(dto.getId());
        mockCustomer.setFullName("John Doe");
        mockCustomer.setAddressId("A001");

        Address mockCurrentAddress = new Address();
        mockCurrentAddress.setAddressId("A001");
        mockCurrentAddress.setMembers(new ArrayList<>(Arrays.asList(new AddressMemberDto(dto.getId(), mockCustomer.getFullName()))));

        Address updatedAddress = new Address();

        when(customerRepository.findById(dto.getId())).thenReturn(Optional.of(mockCustomer));
        when(addressRepository.findById(mockCustomer.getAddressId())).thenReturn(Optional.of(mockCurrentAddress));
        when(sequenceGeneratorService.getSequenceNumber(Address.SEQUENCE_NAME)).thenReturn(1);

        Optional<Customer> updatedCustomer = customerService.updateCustomer(dto);

        assertTrue(updatedCustomer.isPresent());
        assertEquals(dto.getContact(), updatedCustomer.get().getContactNumber());

        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository, times(2)).save(addressCaptor.capture());

        List<Address> savedAddresses = addressCaptor.getAllValues();

        assertEquals("1", savedAddresses.get(0).getAddressId());
        assertEquals("1, Pallansena, Negombo", savedAddresses.get(0).getTextForm());

        List<AddressMemberDto> members = savedAddresses.get(0).getMembers();

        verify(customerRepository, times(1)).save(mockCustomer);
    }

    @Test
    void updateCustomer_ShouldReturnEmpty_WhenCustomerDoesNotExist() {
        ProfileUpdateRequestDto dto = new ProfileUpdateRequestDto();
        dto.setId("C123");

        when(customerRepository.findById(dto.getId())).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.updateCustomer(dto);

        assertFalse(result.isPresent());
        verify(customerRepository, times(0)).save(any(Customer.class));
        verify(addressRepository, times(0)).save(any(Address.class));
    }
}
