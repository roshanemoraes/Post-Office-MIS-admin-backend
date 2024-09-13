package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;


    //The [mongoTemplate.findOne] method is used to query the database.
    // It returns a single document
    // that matches the query criteria, mapping it to the Address class.
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
}
