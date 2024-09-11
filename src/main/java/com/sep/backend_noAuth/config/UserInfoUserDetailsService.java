package com.sep.backend_noAuth.config;

import com.sep.backend_noAuth.entity.UserInfo;
import com.sep.backend_noAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByEmail(username);

        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found!"));
    }
}
