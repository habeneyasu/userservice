package com.ecommerce.userservice.config;

import com.ecommerce.userservice.model.AppUser;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // logger.debug("Entering in loadUserByUsername Method...");
        AppUser user = userService.findByUsername(username);
        if(user == null){
          //  logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
       // logger.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }
}
