package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.domain.*;
import com.d_command.letniy_intensiv.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.LinkedList;
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

    @Autowired
    private TagProjectRepo tagRepo;

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

        model.addAttribute("tags", tagRepo.findAll());
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

    public void addTag(String tag, Project project) {
        if (!project.containsTag(tag)) {
            project.addTag(tagRepo.findByText(tag));
            projectRepo.save(project);
        }
    }

    public void searchProject(String name, Model model) {
        model.addAttribute("search", true);
        if (name.charAt(0) == '#') {
            if (tagRepo.findByText(name.substring(1)) == null) {
                model.addAttribute("error", "Such tag doesn't exist");
                return;
            } else {
                List<Project> projects = projectRepo.findAll();
                List<Project> temp = projectRepo.findAll();
                for (Project item : temp) {
                    if (!item.getTags().contains(tagRepo.findByText(name.substring(1)))) {
                        projects.remove(item);
                    }
                }
                if (projects.isEmpty()) {
                    model.addAttribute("error", "Projects with such tag don't exist");
                    return;
                } else {
                    model.addAttribute("projects", projects);
                }
            }
        } else {
            if (projectRepo.findByName(name) != null) {
                LinkedList<Project> temp = new LinkedList<>();
                temp.add(projectRepo.findByName(name));
                model.addAttribute("projects", temp);
                return;
            } else {
                model.addAttribute("error", "Such project doesn't exist");
                return;
            }
        }
    }
}
