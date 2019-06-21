package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @GetMapping
    public String get_profile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user_now", user);
        return "profile";
    }

    @PostMapping
    public String edit_profile(@RequestParam String username, @RequestParam String password,
                               @AuthenticationPrincipal User user) {
        if (userRepo.findByUsername(username) == null) {
            user.update(username, password);
            userRepo.save(user);
        }

        return "redirect:/profile";
    }
}
