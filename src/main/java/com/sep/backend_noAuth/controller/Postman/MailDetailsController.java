package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.dto.PostMan.MailDetailsDto;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.RouteDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/postman/mail")
public class MailDetailsController {

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private RouteDisplayService routeDisplayService;

    @GetMapping("/get-details")
    public MailDetailsDto getMailDetails (@RequestParam String mailId){
        return routeDisplayService.getMailDetails(mailId);
    }

}
