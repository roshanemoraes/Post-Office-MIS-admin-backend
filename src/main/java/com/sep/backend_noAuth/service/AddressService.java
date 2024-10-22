package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Address> getAddressListToValidate(AddressRequest addressRequest){
        Query query = new Query();
        query.addCriteria(Criteria.where("city").is(addressRequest.getCity()));
        query.addCriteria(Criteria.where("houseNumber").is(addressRequest.getHouseNumber()));
        query.addCriteria(Criteria.where("zone").is(addressRequest.getZone()));
        return  mongoTemplate.find(query, Address.class);
    }
    public List<Address> getAddressToValidate_usingAggregation(AddressRequest addressRequest){
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("city").is(addressRequest.getCity())),
            Aggregation.match(Criteria.where("houseNumber").is(addressRequest.getHouseNumber())),
            Aggregation.match(Criteria.where("zone").is(addressRequest.getZone()))
        );
        AggregationResults<Address> results = mongoTemplate.aggregate(aggregation, "address", Address.class);
        return results.getMappedResults();
    }
    public Address getAddressToValidate(AddressRequest addressRequest){
        Query query = new Query();
        //Criteria objects are built using the values from the AddressRequest
        query.addCriteria(Criteria.where("city").is(addressRequest.getCity()));
        query.addCriteria(Criteria.where("houseNumber").is(addressRequest.getHouseNumber()));
        query.addCriteria(Criteria.where("zone").is(addressRequest.getZone()));
        return  mongoTemplate.findOne(query, Address.class);
    }
    public Address getAddressToValidateDummy(){

        // A Query object is instantiated to build a query for MongoDB
        Query query = new Query();
        query.addCriteria(Criteria.where("city").is("Kochchikade"));
        query.addCriteria(Criteria.where("houseNumber").is("79"));
        query.addCriteria(Criteria.where("zone").is("Pallansena South"));
        return  mongoTemplate.findOne(query, Address.class);
    }
    public void removeMemberByCustomerID(List<AddressMemberDto> members, String customerID) {
        // Using removeIf to remove the member with the given customerID
        members.removeIf(member -> member.getCustomerId().equals(customerID));
    }
    public Optional<Address> updateCustomerAddress(String addressId, String customerId) {
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
            return addressOptional;

        } else {
            // Handle the case where the address is not found (e.g., log an error or throw an exception)
            System.out.println("Address with ID " + addressId + " not found.");
        }

        return null;
    }
}
