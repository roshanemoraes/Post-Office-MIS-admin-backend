package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.dto.Customer.ProfileUpdateRequestDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    public Customer getCustomerInfo(String customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(Long.valueOf(customerId)));
        return mongoTemplate.findOne(query, Customer.class);
    }
    public String[] extractCityAndZone(String addressTextForm) {
        if (addressTextForm != null && !addressTextForm.isEmpty()) {
            return Arrays.stream(addressTextForm.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
        }
        return new String[0];
    }
    public Optional<Customer> updateCustomer(ProfileUpdateRequestDto dto) {
        Optional<Customer> currentCustomer = customerRepository.findById(dto.getId());
        Address updatedAddress = new Address();

        if(currentCustomer.isPresent()){
            Optional<Address> currentAddress = addressRepository.findById(currentCustomer.get().getAddressId());
            if(currentAddress.isPresent()){
                //Updating the address
                updatedAddress.setAddressId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Address.SEQUENCE_NAME)));
                updatedAddress.setTextForm(dto.getAddress());
                updatedAddress.setLat("7.190442");
                updatedAddress.setLng("79.860418");
                String[] houseNoAndZoneAndCityArray = extractCityAndZone(dto.getAddress());
                updatedAddress.setHouseNumber(houseNoAndZoneAndCityArray[0]);
                updatedAddress.setZone(houseNoAndZoneAndCityArray[1]);
                updatedAddress.setCity(houseNoAndZoneAndCityArray[2]);
                AddressMemberDto addressMemberDto = new AddressMemberDto(dto.getId(),currentCustomer.get().getFullName());
                List<AddressMemberDto> members = new ArrayList<>();
                members.add(addressMemberDto);
                updatedAddress.setMembers(members);
                //Updating the customer
                currentCustomer.get().setContactNumber(dto.getContact());
                currentCustomer.get().setAddressId(updatedAddress.getAddressId());

                //saving both the customer and the address
                customerRepository.save(currentCustomer.get());
                addressRepository.save(updatedAddress);

                //Updating the old address(Removing the customer from house member list)
                members.clear();
                members = currentAddress.get().getMembers();
                members.remove(addressMemberDto);
                currentAddress.get().setMembers(members);
                addressRepository.save(currentAddress.get());
                return currentCustomer;
            }
        }
        return Optional.empty();
    }

    /*public void removeMemberByCustomerID(List<AddressMemberDto> members, String customerID) {
        // Using removeIf to remove the member with the given customerID
        members.removeIf(member -> member.getCustomerId().equals(customerID));
    }

    public Customer updateCustomerAddress(String addressId, String customerId) {
        // Find the address by the provided addressId
        Optional<Address> addressOptional = addressRepository.findById(addressId);

        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            List<AddressMemberDto> members = address.getMembers();  // Assuming Address has a getMembers() method

            // Remove the member with the given customerId
            removeMemberByCustomerID(members, customerId);

            // After modifying the list, save the updated Address object back to the repository
            address.setMembers(members);  // Assuming Address has a setMembers() method
            addressRepository.save(address);
        } else {
            // Handle the case where the address is not found (e.g., log an error or throw an exception)
            System.out.println("Address with ID " + addressId + " not found.");
        }

        return null;
    }*/
}
