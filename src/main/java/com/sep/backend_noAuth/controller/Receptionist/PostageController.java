package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.entity.Postage;
import com.sep.backend_noAuth.service.PostageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postage")
public class PostageController {
    @Autowired
    private PostageService postageService;

    @GetMapping("/getPostage")
    public Postage getPostageForWeight(@RequestParam Double weight){
        Postage postage = postageService.findPostageByWeight(weight);;
        return postage;
    }

}
