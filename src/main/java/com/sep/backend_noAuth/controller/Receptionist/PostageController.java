package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.entity.Postage.PostageCourierNormal;
import com.sep.backend_noAuth.entity.Postage.PostageNormalPosts;
import com.sep.backend_noAuth.entity.Postage.PostageParcelGov;
import com.sep.backend_noAuth.entity.Postage.PostageParcelNormal;
import com.sep.backend_noAuth.service.PostageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postage")
public class PostageController {
    @Autowired
    private PostageService postageService;

    @GetMapping("/getPostage")
    public PostageNormalPosts getPostageForWeight(@RequestParam Double weight){
        PostageNormalPosts postage = postageService.findPostageByWeight(weight);
        return postage;
    }
    @GetMapping("/list/normal-post")
    public List<PostageNormalPosts> getPostageListForNormalPost(){
        return postageService.getNormalPostageList();
    }@GetMapping("/list/courier-normal")
    public List<PostageCourierNormal> getPostageListForCourierNormal(){
        return postageService.getCourierNormalPostageList();
    }@GetMapping("/list/parcel-gov")
    public List<PostageParcelGov> getPostageListParcelGov(){
        return postageService.getParcelGovPostageList();
    }@GetMapping("/list/parcel-normal")
    public List<PostageParcelNormal> getPostageListForParcelNormal(){
        return postageService.getParcelNormalPostageList();
    }

}
