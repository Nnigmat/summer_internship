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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    //    Supported file extensions: JPEG.; GIF, including animated GIFs.; PNG.; APNG.; SVG.; BMP.; BMP ICO.; PNG ICO.
//    Maximum file size is 1MB
    @PostMapping("/upload")
    public String upload_image(@RequestParam MultipartFile file,
                               @AuthenticationPrincipal User user) throws IOException {
        userService.updateImage(user, file);

        return "redirect:/profile";
    }

    @PostMapping("/tag")
    public String add_tag(@RequestParam String tag, @AuthenticationPrincipal User user) {
        userService.addTag(tag, user);

        return "redirect:/profile";
    }
}
