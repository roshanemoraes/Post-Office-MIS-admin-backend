package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.dto.AuthRequest;
import com.sep.backend_noAuth.dto.AuthResponseDto;
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
        System.out.println("Auth req came to backend");
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
            }
            AuthResponseDto authResponseDto = new AuthResponseDto(name,email,role,"Authentication Success!");
            return ResponseEntity.ok(authResponseDto);
        }else{
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
