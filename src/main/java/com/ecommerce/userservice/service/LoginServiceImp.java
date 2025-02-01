package com.ecommerce.userservice.service;

import com.ecommerce.userservice.modeldto.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService{

    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Override
    public String login(Login login){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken( login.getUsername());

        return token;
    }
}
