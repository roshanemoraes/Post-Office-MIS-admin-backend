package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.PostMan.AddPersonDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.PostmanAssignment;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.PostmanAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/postman/add-person")
public class AddPersonController {

        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private PostmanAssignmentRepository postmanAssignmentRepository;

        @PostMapping("/")
        public ResponseEntity<String> addPerson(@RequestBody AddPersonDto addPersonDto) {
                // Find address by custom addressId field in MongoDB
                Address address = addressRepository.findByAddressId(addPersonDto.getAddressId());

                if (address!=null) {
                        // Add members to the address
                        List<AddressMemberDto> members = address.getMembers();
                        List<AddressMemberDto> newMembers = addPersonDto.getMembers();
                        for (AddressMemberDto member1 : newMembers) {
                                member1.setCustomerId("unreg");
                                members.add(member1);
                        }
                        address.setMembers(members);
                        addressRepository.save(address);
                        return ResponseEntity.ok("Members added successfully");
                } else {
                        return ResponseEntity.status(404).body("Address not found with id: " + addPersonDto.getAddressId());
                }
        }
        @GetMapping("/get-zone")
        public ResponseEntity<String> getZone(@RequestParam String postmanId){
                Optional<PostmanAssignment> assignment = postmanAssignmentRepository.findByPostmanId(Long.valueOf(postmanId));
                if(assignment.isPresent()) {
                        System.out.println(assignment.get().getZone());
                        return ResponseEntity.ok(assignment.get().getZone());
                }
                return ResponseEntity.status(404).body("Postman not found with id: " + postmanId);
        }
}
