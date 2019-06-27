package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.domain.*;
import com.d_command.letniy_intensiv.repos.CommentRepo;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import com.d_command.letniy_intensiv.repos.TeamRepo;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private TeamRepo teamRepo;

    public void findByType(ProjectType type, Model model) {
        Iterable<Project> projects = null;
        if (type != null) {
            projects = projectRepo.findByType(type);
        } else {
            projects = projectRepo.findAll();
        }

        model.addAttribute("projects", projects);
    }

    public void create(String name, String description, User user) {
        if (name == "" || projectRepo.findByName(name) != null) {
            //error msg
        } else {
            projectRepo.save(new Project(name, description, user));
        }
    }

    public void projectInfo(Project project, Intensive intensive, Model model) {
        model.addAttribute("project", project);
        model.addAttribute("intensive", intensive);
        model.addAttribute("team", teamRepo.findByProjectAndIntensive(project, intensive));

        List<User> supervisors = userRepo.findAll();
        List<User> ff = userRepo.findAll();
        for (User item : ff) {
            if (!item.isCurator()) {
                supervisors.remove(item);
            }
        }
        model.addAttribute("supervisors", supervisors);
        model.addAttribute("isEmptySupervisor", supervisors.isEmpty());

        if (intensive != null) {
            List<User> team = userRepo.findAll();
            for (User item : ff) {
                if (!item.getRoles().contains(Role.USER) ||
                        teamRepo.findByProjectAndIntensive(project, intensive).getParticipants().contains(item)) {
                    team.remove(item);
                }
            }
            model.addAttribute("available_users", team);
            model.addAttribute("isEmptyTeam", team.isEmpty());
        }

        model.addAttribute("types", ProjectType.values());

        List<Comment> project_comments = commentRepo.findAll();
        List<Comment> temp = commentRepo.findAll();
        for (Comment comment : project_comments) {
            if (comment.getProject().getId() != project.getId()) {
                temp.remove(comment);
            }
        }
        model.addAttribute("comments", temp);
    }

    public void update(Project project, String name, String description, User user) {
        if (user.isModerator() || project.isCreator(user)) {
            project.update(name, description);
            projectRepo.save(project);
        }
    }


    public void addParticipant(Project project, Intensive intensive, String username) {
        Team team = teamRepo.findByProjectAndIntensive(project, intensive);
        team.addParticipant(userRepo.findByUsername(username));
        teamRepo.save(team);
    }

    public void addComment(Project project, String text, User user) {
        if (!text.equals("")) {
            commentRepo.save(new Comment(project, text, user));
        }
    }

    public void addSupervisor(Project project, Intensive intensive, String username) {
        Team team = teamRepo.findByProjectAndIntensive(project, intensive);
        team.setSupervisor(userRepo.findByUsername(username));
        teamRepo.save(team);
    }

    public void changeType(Project project, String type) {
        switch (type) {
            case "NEW":
                project.getType().clear();
                project.getType().add(ProjectType.NEW);
                break;
            case "ACCEPTED":
                project.getType().clear();
                project.getType().add(ProjectType.ACCEPTED);
                break;
            case "DECLINED":
                project.getType().clear();
                project.getType().add(ProjectType.DECLINED);
                break;
            case "ARCHIVED":
                project.getType().clear();
                project.getType().add(ProjectType.ARCHIVED);
                break;
        }
        projectRepo.save(project);
    }

    public void delete(Project project) {
        projectRepo.delete(project);
    }

    public void upvote(Project project, User user) {
        if (!project.getWho_liked().contains(userRepo.findByUsername(user.getUsername()))) {
            project.addLike(user);
            projectRepo.save(project);
        }
    }
}
