package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.ProjectType;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.IntensiveRepo;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class IntensiveService {
    @Autowired
    private IntensiveRepo intensiveRepo;

    @Autowired
    private ProjectRepo projectRepo;


    public List<Intensive> findAll() {
        return intensiveRepo.findAll();
    }

    public void create(String name, String description, String date_end, String date_start, User user) {
        intensiveRepo.save(new Intensive(name, description, date_end, date_start, user));
    }

    public void intensiveInfo(Intensive intensive, Model model) {
        model.addAttribute("intensive", intensive);
        model.addAttribute("projects", intensive.getProject_list());
        List<Project> temp = projectRepo.findAll();
        List<Project> all_projects = projectRepo.findAll();
        for (Project project : temp) {
            if (!project.getType().contains(ProjectType.ACCEPTED) || project.getIntensive_list().contains(intensive)) {
                all_projects.remove(project);
            }
        }
        model.addAttribute("isEmpty", all_projects.isEmpty());
        model.addAttribute("all_projects", all_projects);
    }

    public void addProject(Intensive intensive, String project) {
        intensive.addProject(projectRepo.findByName(project));
        intensiveRepo.save(intensive);
    }

    public void update(Intensive intensive, String name, String description, String date_start, String date_end) {
        intensive.update(name, description, date_start, date_end);

        intensiveRepo.save(intensive);
    }
}
