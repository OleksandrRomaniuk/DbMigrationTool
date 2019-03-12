package com.dbbest.controllers;


import com.dbbest.models.LoginCredentials;
import com.dbbest.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login")
    private boolean checkTree(@RequestBody LoginCredentials loginCredentials, HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession httpSession) {
        boolean check = loginService.checkLoginAndPassword(
                loginCredentials.getLogin(),
                loginCredentials.getPassword(),
                loginCredentials.getDbName(),
                loginCredentials.getDbType()
        );
        return check;
    }
}
