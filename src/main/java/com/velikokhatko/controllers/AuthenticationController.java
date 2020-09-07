package com.velikokhatko.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    private String authPage;

    public AuthenticationController() {
    }

    @GetMapping("/oauth2/authorization/google")
    public String processCreateOrUpdateUserForm() {
        return "redirect:/users/home";
    }
}

