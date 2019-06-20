package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.IntensiveRepo;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/intensive")
public class IntensiveController {
    @Autowired
    private IntensiveRepo intensiveRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @GetMapping
    public String intensive_list(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("intensives", intensiveRepo.findAll());
        model.addAttribute("user_now", user);

        return "intensive_list";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CURATOR')")
    public String create_intensive(@RequestParam String name, @RequestParam String description,
                                   @RequestParam String date_start, @RequestParam String date_end,
                                   @AuthenticationPrincipal User user) {
        intensiveRepo.save(new Intensive(name, description, date_end, date_start, user));

        return "redirect:/intensive";
    }

    @GetMapping("/{intensive}")
    public String intensive_info(Model model, @AuthenticationPrincipal User user,
                                 @PathVariable Intensive intensive) {
        model.addAttribute("intensive", intensive);
        model.addAttribute("projects", intensive.getProject_list());
        model.addAttribute("user_now", user);
        model.addAttribute("all_projects", projectRepo.findAll());

        return "intensive_info";
    }

    @PostMapping("/{intensive}")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String add_project_to_intensive(@PathVariable Intensive intensive,
                                           @RequestParam String project) {
        intensive.addProject(projectRepo.findByName(project));
        intensiveRepo.save(intensive);

        return "redirect:/intensive/{intensive}";
    }
}
