package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Role;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String user_list(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("user_now", user);

        return "user_list";
    }

    @PostMapping
    public String search_user(@RequestParam String username, Model model,
                              @AuthenticationPrincipal User user) {
        if (username.equals("")) {
            model.addAttribute("users", userRepo.findAll());
        } else {
            if (userRepo.findByUsername(username) != null) {
                LinkedList<User> temp = new LinkedList<>();
                temp.add(userRepo.findByUsername(username));
                model.addAttribute("users", temp);
            } else {
                model.addAttribute("error", "Such user doesn't exist");
            }
        }
        model.addAttribute("user_now", user);

        return "user_list";
    }

    @GetMapping("/{user}/mod")
    public String make_mod(@PathVariable User user) {
        if (user.isModerator()) {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.MODERATOR);
        }
        userRepo.save(user);

        return "redirect:/user";
    }

    @GetMapping("/{user}/cur")
    public String make_curator(@PathVariable User user) {
        if (user.isCurator()) {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.CURATOR);
        }
        userRepo.save(user);

        return "redirect:/user";
    }

    @GetMapping("/{user}/ban")
    public String ban_user(@PathVariable User user) {
        if (user.isBanned()) {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.BAN);
        }
        userRepo.save(user);

        return "redirect:/user";
    }
}

