package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String project_list(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("projects", projectRepo.findAll());
        model.addAttribute("user_now", user);

        return "project_list";
    }

    @PostMapping
    public String create_project(@RequestParam String name, @RequestParam String description,
                                 @AuthenticationPrincipal User user) {
        projectRepo.save(new Project(name, description, user));

        return "redirect:/project";
    }

    @GetMapping("/{project}")
    public String project_info(Model model, @AuthenticationPrincipal User user,
                               @PathVariable Project project) {
        model.addAttribute("project", project);
        model.addAttribute("user_now", user);
        model.addAttribute("all_users", userRepo.findAll());

        return "project_info";
    }

    @PostMapping("/{project}/edit")
    public String project_edit(@PathVariable Project project, @RequestParam String name,
                               @RequestParam String description) {
        projectRepo.save(project.update(name, description));

        return "redirect:/project";
    }

    @PostMapping("/{project}/add")
    public String project_add_user(@PathVariable Project project, @RequestParam String username) {
        project.addUser(userRepo.findByUsername(username));
        projectRepo.save(project);

        return "redirect:/project/{project}";
    }
}
