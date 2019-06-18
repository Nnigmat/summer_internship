package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.repos.IntensiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/intensive")
public class IntensiveController {
    @Autowired
    private IntensiveRepo intensiveRepo;

    @GetMapping
    public String list(Model model) {
        Iterable<Intensive> intensives = intensiveRepo.findAll();

        model.addAttribute("intensives", intensives);

        return "intensive_list";
    }
}
