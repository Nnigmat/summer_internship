package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String get_profile(@AuthenticationPrincipal User user, Model model) {
        userService.profileInfo(user, model);
        model.addAttribute("user_now", user);

        return "profile";
    }

    @PostMapping
    public String edit_profile(@RequestParam String username, @RequestParam String password,
                               @RequestParam String name, @RequestParam String surname,
                               @AuthenticationPrincipal User user) {
        userService.update(user, username, password, name, surname);

        return "redirect:/profile";
    }
}
