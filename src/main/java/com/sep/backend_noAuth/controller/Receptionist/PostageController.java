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

    @GetMapping("/get/normal-post")
    public PostageNormalPosts getNormalPostPostageForWeight(@RequestParam Double weight){
        PostageNormalPosts postage = postageService.findNormalPostPostageByWeight(weight);
        return postage;
    }
    @GetMapping("/get/courier-normal")
    public PostageCourierNormal getCourierNormalPostageForWeight(@RequestParam Double weight){
        PostageCourierNormal postage = postageService.findCourierNormalPostageByWeight(weight);
        return postage;
    }
    @GetMapping("/get/parcel-normal")
    public PostageParcelNormal getParcelNormalPostageForWeight(@RequestParam Double weight){
        PostageParcelNormal postage = postageService.findParcelNormalPostageByWeight(weight);
        return postage;
    }
    @GetMapping("/get/parcel-gov")
    public PostageParcelGov getParcelGovPostageForWeight(@RequestParam Double weight){
        PostageParcelGov postage = postageService.findParcelGovPostageByWeight(weight);
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
