package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.repos.RegIDRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    RegIDRepo regIDRepo;

    @GetMapping("/{UUID}")
    public String regForm(Model model, @PathVariable Long UUID) {
        Long id = regIDRepo.findAll().get(0).getId();
        if (id.equals(UUID)) {
            return "registration";
        }
        else {
            return "redirect:/";
        }
    }
}
