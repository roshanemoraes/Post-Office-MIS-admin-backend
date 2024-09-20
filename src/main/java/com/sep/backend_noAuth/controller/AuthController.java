package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.dto.AuthRequest;
import com.sep.backend_noAuth.dto.AuthResponseDto;
import com.sep.backend_noAuth.dto.PostMan.AuthResponseMobileDto;
import com.sep.backend_noAuth.entity.UserInfo;
import com.sep.backend_noAuth.repository.UserRepository;
import com.sep.backend_noAuth.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userInfoRepository;

    @PostMapping("/admin/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("User valid!");
            String token = jwtService.generateToken(authRequest.getUsername());

            Cookie cookie = new Cookie("jwt",token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
            String name = null;
            String role = null;
            if(userInfo.isPresent()){
                name = userInfo.get().getName();
                role = userInfo.get().getRoles();
                if(userInfo.get().getRoles().equals("ROLE_CUSTOMER") || userInfo.get().getRoles().equals("ROLE_POSTMAN"))
                    throw new UsernameNotFoundException("Invalid user request");
            }
            AuthResponseDto authResponseDto = new AuthResponseDto(name,email,role,"Authentication Success!");
            return ResponseEntity.ok(authResponseDto);
        }else{
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateCustomerAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUsername());

            Cookie cookie = new Cookie("jwt",token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
            String name = null;
            String role = null;
            if(userInfo.isPresent()){
                name = userInfo.get().getName();
                role = userInfo.get().getRoles();
                if(!userInfo.get().getRoles().equals("ROLE_CUSTOMER") )
                    throw new UsernameNotFoundException("Invalid user request");
            }
            System.out.println("User-customer valid!");
            AuthResponseDto authResponseDto = new AuthResponseDto(name,email,role,"Authentication Success!");
            return ResponseEntity.ok(authResponseDto);
        }else{
            throw new UsernameNotFoundException("Invalid user request");
        }
    }


    @PostMapping("/mobile/authenticate")
    public ResponseEntity<?> authenticateMobileAndGetToken(@RequestBody AuthRequest authRequest){
        System.out.println("came");
        System.out.println(authRequest.getUsername());
        System.out.println(authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUsername());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
            String name = null;
            String role = null;
            String postmanId = null;
            if(userInfo.isPresent()){
                name = userInfo.get().getName();
                role = userInfo.get().getRoles();
                postmanId = userInfo.get().getId();
                if(!userInfo.get().getRoles().equals("ROLE_POSTMAN") ){
                    throw new UsernameNotFoundException("Invalid user request");
                }
            }
            System.out.println("User valid for mobile!");
            AuthResponseMobileDto authResponseDto = new AuthResponseMobileDto(name,postmanId, email, role, "Authentication Success!", token);
            return ResponseEntity.ok(authResponseDto);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

}
