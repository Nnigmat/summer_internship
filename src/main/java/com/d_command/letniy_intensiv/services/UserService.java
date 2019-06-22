package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.Role;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.ProjectRepo;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepo.findByUsername(username);
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void searchUser(String username, Model model) {
        if (username.equals("")) {
            model.addAttribute("users", userRepo.findAll());
        } else {
            if (userRepo.findByUsername(username) != null) {
                LinkedList<User> temp = new LinkedList<>();
                temp.add(userRepo.findByUsername(username));
                model.addAttribute("users", temp);
            } else {
                model.addAttribute("error", "Such user doesn't exist");
            }
        }
    }

    public void changeAuthority(User user, Role role) {
        if (user.getRoles().contains(role)) {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        } else {
            user.getRoles().clear();
            user.getRoles().add(role);
        }
        userRepo.save(user);
    }

    public void update(User user, String username, String password, String name, String surname) {
        if (userRepo.findByUsername(username) == null) {
            user.update(username, password, name, surname);
            userRepo.save(user);
        }
    }

    public void profileInfo(User user, Model model) {
        if (user.isCurator()) {
            List<Project> projects = projectRepo.findAll();
            List<Project> temp = projectRepo.findAll();
            for (Project item : temp) {
                if (item.getSupervisor() == null || item.getSupervisor().getId() != user.getId()) {
                    projects.remove(item);
                }
            }
            model.addAttribute("projects", projects);
        } else {
            model.addAttribute("projects", user.getProject_list());
        }
    }
}
