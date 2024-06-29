package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.entity.DeliveryManager.UndeliverableMail;
import com.sep.backend_noAuth.repository.UndeliverableMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery-manager/return-mail")
public class ReturnMailController {

    @Autowired
    private UndeliverableMailRepository undeliverableMailRepository;

//    @GetMapping("list-all")
//    public List<Map<String, Object>> getReturnMailList() {
//        List<Map<String, Object>> returnMailList = Arrays.asList(
//                Map.of(
//                        "id", "137",
//                        "type", "Normal Post",
//                        "zone", "Pallansena South",
//                        "city", "Kochchikade",
//                        "reason", "Invalid Recipient Name",
//                        "returnDate", "2024-04-15"
//                ),
//                Map.of(
//                        "id", "144",
//                        "type", "Personal Courier",
//                        "zone", "Katana",
//                        "city", "Kochchikade",
//                        "reason", "Damaged",
//                        "returnDate", "2024-04-25"
//                ),
//                Map.of(
//                        "id", "91",
//                        "type", "Personal Parcel",
//                        "zone", "Kattuwa",
//                        "city", "Negombo",
//                        "reason", "Over weight",
//                        "returnDate", "2024-03-15"
//                ),
//                Map.of(
//                        "id", "111",
//                        "type", "Registered Post",
//                        "zone", "Pallansena North",
//                        "city", "Kochchikade",
//                        "reason", "Invalid Address",
//                        "returnDate", "2024-01-15"
//                )
//        );
//        return returnMailList;
//    }
//    @GetMapping("/list-all")
//    public Optional<List<UndeliverableMail>> getAllMails(){
//        List<UndeliverableMail> list = undeliverableMailRepository.findAll();
//        return Optional.of(list);
//    }
    @GetMapping("/list-all")
    public Optional<List<UndeliverableMail>> getAllUndeliveredMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Undelivered");
        return Optional.of(list);
    }
    @GetMapping("/return-to-sender")
    public Optional<List<UndeliverableMail>> getReturnToSenderUndeliveredMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Return-to-Sender");
        return Optional.of(list);
    }
    @GetMapping("/address-update")
    public Optional<List<UndeliverableMail>> getAddressUpdateUndeliveredMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Address-Update");
        return Optional.of(list);
    }
    @PostMapping("/add/return-to-sender")
    public ResponseEntity<String> addToReturnToSender(@RequestBody String mailId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByMailId(mailId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Return-to-Sender");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Successfully Updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add/address-update")
    public ResponseEntity<String> addToAddressUpdate(@RequestBody String mailId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByMailId(mailId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Address-Update");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Successfully Updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/add")
    public UndeliverableMail testAdd(){
        UndeliverableMail undeliverableMail = new UndeliverableMail(
                "137",
                "Undelivered",
                "Normal Post",
                "Pallansena South",
                "Kochchikade",
                "Invalid Recipient Name",
                "Emp003",
                "2024-04-15");
        undeliverableMailRepository.save(undeliverableMail);
        return undeliverableMail;
    }
}
