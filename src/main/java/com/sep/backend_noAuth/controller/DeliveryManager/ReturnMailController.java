package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.entity.DeliveryManager.UndeliverableMail;
import com.sep.backend_noAuth.repository.UndeliverableMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    @GetMapping("/list-all")
    public Optional<List<UndeliverableMail>> getAllMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findAll();
        return Optional.of(list);
    }
}
