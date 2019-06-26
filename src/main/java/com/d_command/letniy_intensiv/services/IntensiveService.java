package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.domain.*;
import com.d_command.letniy_intensiv.repos.IntensiveRepo;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import com.d_command.letniy_intensiv.repos.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class IntensiveService {
    private final IntensiveRepo intensiveRepo;
    private final ProjectRepo projectRepo;
    private final TeamRepo teamRepo;

    @Autowired
    public IntensiveService(IntensiveRepo intensiveRepo, ProjectRepo projectRepo, TeamRepo teamRepo) {
        this.intensiveRepo = intensiveRepo;
        this.projectRepo = projectRepo;
        this.teamRepo = teamRepo;
    }

    public List<Intensive> findAll() {
        return intensiveRepo.findAll();
    }

    public List<Intensive> findByName(String name) { return intensiveRepo.findByName(name); }

    public void create(String name, String description, String date_end, String date_start, User user) {
        if (name == null || name.isEmpty()) {
            //error msg, but i'm lazy
        } else {
            intensiveRepo.save(new Intensive(name, description, date_end, date_start, user));
        }
    }

    public void intensiveInfo(Intensive intensive, Model model) {
        model.addAttribute("intensive", intensive);

        List<Team> teams = teamRepo.findByIntensive(intensive);
        LinkedList<Project> projects = new LinkedList<>();
        for (Team item : teams) {
            projects.add(item.getProject());
        }
        model.addAttribute("projects", projects);

        List<Project> temp = projectRepo.findAll();
        List<Project> all_projects = projectRepo.findAll();
        for (Project project : temp) {
            if (!project.getType().contains(ProjectType.ACCEPTED) ||
                    teamRepo.findByProjectAndIntensive(project, intensive) != null) {
                all_projects.remove(project);
            }
        }
        model.addAttribute("isEmpty", all_projects.isEmpty());
        model.addAttribute("all_projects", all_projects);
    }

    public void addProject(Intensive intensive, String project) {
        teamRepo.save(new Team(intensive, projectRepo.findByName(project)));
    }

    public void update(Intensive intensive, String name, String description, String date_start, String date_end) {
        intensive.update(name, description, date_start, date_end);

        intensiveRepo.save(intensive);
    }

    public void removeAll(List<Intensive> intensives) {
        for (Intensive inten: intensives) {
            intensiveRepo.delete(inten);
        }
    }

}
