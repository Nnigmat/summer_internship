package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Role;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String user_list(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user_now", user);
        model.addAttribute("uuid", userService.getInvLink());

        return "user_list";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String search_user(@RequestParam String username, Model model,
                              @AuthenticationPrincipal User user) {
        userService.searchUser(username, model);
        model.addAttribute("user_now", user);

        return "user_list";
    }

    @GetMapping("/{user}/mod")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String make_mod(@PathVariable User user) {
        userService.changeAuthority(user, Role.MODERATOR);

        return "redirect:/user";
    }

    @GetMapping("/{user}/cur")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String make_curator(@PathVariable User user) {
        userService.changeAuthority(user, Role.CURATOR);

        return "redirect:/user";
    }

    @GetMapping("/{user}/ban")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String ban_user(@PathVariable User user) {
        userService.changeAuthority(user, Role.BAN);

        return "redirect:/user";
    }

    @PostMapping("/update_link")
    public String update_link() {
        userService.updateLink();

        return "redirect:/user";
    }
}

