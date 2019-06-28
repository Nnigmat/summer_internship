package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.ProjectType;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/project")
    public String project_list(Model model, @AuthenticationPrincipal User user,
                               @RequestParam(required = false) ProjectType type) {
        projectService.findByType(type, model);
        model.addAttribute("user_now", user);

        return "project_list";
    }

    @PostMapping("/project")
    public String create_project(@RequestParam String name, @RequestParam String description,
                                 @AuthenticationPrincipal User user) {
        projectService.create(name, description, user);

        return "redirect:/project";
    }

    @GetMapping("/project/{project}")
    public String project_info(Model model, @AuthenticationPrincipal User user,
                               @PathVariable Project project) {
        projectService.projectInfo(project, null, model);
        model.addAttribute("user_now", user);

        return "project_info";
    }

    @GetMapping("/intensive/{intensive}/project/{project}")
    public String project_info_in_intensive(Model model, @AuthenticationPrincipal User user,
                                            @PathVariable Project project, @PathVariable Intensive intensive) {
        projectService.projectInfo(project, intensive, model);
        model.addAttribute("user_now", user);

        return "project_intensive_info";
    }

    @PostMapping("/project/{project}/edit")
    public String project_edit(@PathVariable Project project, @RequestParam String name,
                               @RequestParam String description, @AuthenticationPrincipal User user) {
        projectService.update(project, name, description, user);
        return "redirect:/project/" + project.getId();
    }

    @PostMapping("/intensive/{intensive}/project/{project}/add")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String project_add_user(@PathVariable Project project, @RequestParam String username,
                                   @PathVariable Intensive intensive) {
        projectService.addParticipant(project, intensive, username);

        return "redirect:/intensive/{intensive}/project/{project}";
    }

    @PostMapping("/project/{project}/comment")
    public String add_comment(@PathVariable Project project, @RequestParam String text,
                              @AuthenticationPrincipal User user) {
        projectService.addComment(project, text, user);

        return "redirect:/project/{project}";
    }

    @PostMapping("/intensive/{intensive}/project/{project}/comment")
    public String add_comment(@PathVariable Project project, @RequestParam String text,
                              @AuthenticationPrincipal User user, @PathVariable Intensive intensive) {
        projectService.addComment(project, text, user);

        return "redirect:/intensive/{intensive}/project/{project}";
    }

    @PostMapping("/intensive/{intensive}/project/{project}/supervisor")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String add_supervisor(@PathVariable Project project, @RequestParam String supervisor,
                                 @PathVariable Intensive intensive) {
        projectService.addSupervisor(project, intensive, supervisor);

        return "redirect:/intensive/{intensive}/project/{project}";
    }

    @PostMapping("/project/{project}/type")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String change_type(@PathVariable Project project, @RequestParam String type) {
        projectService.changeType(project, type);

        return "redirect:/project/{project}";
    }

    @PostMapping("/intensive/{intensive}/project/{project}/type")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String change_type(@PathVariable Project project, @RequestParam String type,
                              @PathVariable Intensive intensive) {
        projectService.changeType(project, type);

        return "redirect:/intensive/{intensive}/project/{project}";
    }

    @PostMapping("/project/{project}/delete")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String delete_project(@PathVariable Project project) {
        projectService.delete(project);

        return "redirect:/project";
    }

    @GetMapping("/project/{project}/like")
    public String like_project(@PathVariable Project project, @AuthenticationPrincipal User user) {
        projectService.upvote(project, user);

        return "redirect:/project";
    }

    @PostMapping("/project/{project}/tag")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String add_tag(@PathVariable Project project, @RequestParam String tag) {
        projectService.addTag(tag, project);

        return "redirect:/project/{project}";
    }

    @PostMapping("/project/search")
    public String search_project(@RequestParam String name, Model model,
                                 @AuthenticationPrincipal User user) {
        if (name.equals("")) {
            return "redirect:/project";
        } else {
            projectService.searchProject(name, model);
            model.addAttribute("user_now", user);

            return "project_list";
        }
    }
}
