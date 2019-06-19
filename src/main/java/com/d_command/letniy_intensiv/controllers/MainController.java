package com.d_command.letniy_intensiv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String intensive_list() {
        return "main_page";
    }
}
