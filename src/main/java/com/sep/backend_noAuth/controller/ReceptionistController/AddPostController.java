package com.sep.backend_noAuth.controller.ReceptionistController;

import com.sep.backend_noAuth.dto.PostTypes.NormalPost;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/receptionist/post/add/")
public class AddPostController {
    @PostMapping("/normal-post")
    public ResponseEntity<String> addNormalPost(@RequestBody NormalPost normalPost){
        System.out.println(normalPost);
        return ResponseEntity.ok("Successfully Saved.");
    }
}
