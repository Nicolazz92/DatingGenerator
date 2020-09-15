package com.velikokhatko.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartPageController {

    public StartPageController() {
    }

    @GetMapping("/")
    public String startPage() {
        return "redirect:/users/home";
    }
}