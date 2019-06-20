package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Comment;
import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.ProjectType;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.CommentRepo;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @GetMapping
    public String project_list(Model model, @AuthenticationPrincipal User user, @RequestParam(required = false) ProjectType type) {
        Iterable<Project> projects = null;
        if (type != null) {
            projects = projectRepo.findByType(type);
        }
        if (projects == null) {
            projects = projectRepo.findAll();
        }

        model.addAttribute("projects", projects);
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

        List<Comment> project_comments = commentRepo.findAll();
        List<Comment> temp = commentRepo.findAll();
        for (Comment comment : project_comments) {
            if (comment.getProject().getId() != project.getId()) {
                temp.remove(comment);
            }
        }
        model.addAttribute("comments", temp);

        return "project_info";
    }

    @PostMapping("/{project}/edit")
    @PreAuthorize("hasAnyAuthority('CURATOR', 'MODERATOR')")
    public String project_edit(@PathVariable Project project, @RequestParam String name,
                               @RequestParam String description) {
        project.update(name, description);
        projectRepo.save(project);

        return "redirect:/project/" + project.getId();
    }

    @PostMapping("/{project}/add")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String project_add_user(@PathVariable Project project, @RequestParam String username) {
        project.addUser(userRepo.findByUsername(username));
        projectRepo.save(project);

        return "redirect:/project/{project}";
    }

    @PostMapping("/{project}/comment")
    public String add_comment(@PathVariable Project project, @RequestParam String text,
                              @AuthenticationPrincipal User user) {
        commentRepo.save(new Comment(project, text, user));

        return "redirect:/project/{project}";
    }

    @PostMapping("/{project}/supervisor")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String add_supervisor(@PathVariable Project project, @RequestParam String username) {
        project.update(userRepo.findByUsername(username));
        projectRepo.save(project);

        return "redirect:/project/{project}";
    }
}
