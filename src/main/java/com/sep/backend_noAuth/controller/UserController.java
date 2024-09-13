package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.dto.UserAuth;
import com.sep.backend_noAuth.entity.UserInfo;
import com.sep.backend_noAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public Object authenticate(@RequestBody UserAuth userAuth){
        Optional<UserInfo> user = userRepository.findByEmail(userAuth.getEmail());
        if(user.isPresent() )
            if(userAuth.getPassword().equals(user.get().getPassword()))
                return user;
        return new Error("user not found!");
    }
}
