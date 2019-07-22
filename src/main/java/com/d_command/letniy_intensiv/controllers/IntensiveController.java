package com.d_command.letniy_intensiv.controllers;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.services.IntensiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/intensive")
public class IntensiveController {
    @Autowired
    private IntensiveService intensiveService;

    @GetMapping
    public String intensiveList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("intensives", intensiveService.findAll());
        model.addAttribute("user_now", user);

        return "intensive_list";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CURATOR')")
    public String createIntensive(@RequestParam String name, @RequestParam String description,
                                   @RequestParam String date_start, @RequestParam String date_end,
                                   @AuthenticationPrincipal User user) {
        intensiveService.create(name, description, date_end, date_start, user);

        return "redirect:/intensive";
    }

    @GetMapping("/{intensive}")
    public String intensiveInfo(Model model, @AuthenticationPrincipal User user,
                                 @PathVariable Intensive intensive) {
        intensiveService.intensiveInfo(intensive, model);
        model.addAttribute("user_now", user);

        return "intensive_info";
    }

    @PostMapping("/{intensive}")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String addProjectToIntensive(@PathVariable Intensive intensive,
                                        @RequestParam String project_name) {
        intensiveService.addProject(intensive, project_name);

        return "redirect:/intensive/{intensive}";
    }

    @PostMapping("/{intensive}/edit")
    @PreAuthorize("hasAuthority('CURATOR')")
    public String editIntensive(@PathVariable Intensive intensive, @RequestParam String name,
                                @RequestParam String description, @RequestParam String date_start, @RequestParam String date_end) {
        intensiveService.update(intensive, name, description, date_start, date_end);

        return "redirect:/intensive/{intensive}";
    }

    @PostMapping("/{intensive}/upload")
    public String upload_file(@RequestParam MultipartFile file, @PathVariable Intensive intensive,
                              @AuthenticationPrincipal User user) throws IOException {
        intensiveService.uploadFile(intensive, user, file);

        return "redirect:/intensive/{intensive}";
    }
}
