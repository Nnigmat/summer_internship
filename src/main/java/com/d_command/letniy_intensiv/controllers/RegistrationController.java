package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.repos.RegIDRepo;
import com.d_command.letniy_intensiv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private RegIDRepo regIDRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/{UUID}")
    public String regForm(@PathVariable String UUID, Model model) {
        UUID id = regIDRepo.findAll().get(0).getId();
        model.addAttribute("uuid", id);
        if (id.toString().equals(UUID)) {
            return "registration";
        }
        else {
            return "redirect:/";
        }
    }

    @PostMapping("/{UUID}")
    public String fillForm(@PathVariable String UUID, @RequestParam String username,
                           @RequestParam String password, @RequestParam String name,
                           @RequestParam String surname) {
        userService.create(username, password, name, surname);

        return "redirect:/login";
    }
}
