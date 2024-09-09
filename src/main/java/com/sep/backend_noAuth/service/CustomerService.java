package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.dto.Customer.ProfileUpdateRequestDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

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

    public Customer getCustomerInfo(String customerId){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(Long.valueOf(customerId)));
        return mongoTemplate.findOne(query, Customer.class);
    }
    public Optional<Customer> updateCustomer(ProfileUpdateRequestDto dto){
        Optional<Customer> customer = customerRepository.findById(dto.getId());
        Customer updatedCustomer;
        if(customer.isPresent()){
            updatedCustomer = customer.get();
            updatedCustomer.setContactNumber(dto.getContact());
            customerRepository.save(updatedCustomer);
            return Optional.of(updatedCustomer);
        }
        else{
            return Optional.empty();
        }
    }
    public void updateCustomerAddress(String addressId){
        Optional<Address> address = addressRepository.findById(addressId);
        //if(address.isPresent()){
            //List<>
        //}
    }

}
