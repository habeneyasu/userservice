package com.ecommerce.userservice.service;

import com.ecommerce.userservice.modeldto.Login;

public interface LoginService {

    /**
     * Loging service using username and password
     */
    public String login(Login login);

}
