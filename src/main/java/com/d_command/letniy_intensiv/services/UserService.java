package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.domain.Role;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.TeamRepo;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Value("${upload.path}")
    private String upload_path;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepo.findByUsername(username);
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void create(String username, String password, String name, String surname) {
        if (userRepo.findByUsername(username) == null && !username.equals("")) {
            userRepo.save(new User(username, password, name, surname));
        }
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

    public void updateImage(User user, MultipartFile file) throws IOException {
        if (file != null) {
            String name = file.getOriginalFilename();
            if (name.equals("")) {
                //error empty
                return;
            }
            while (name.contains(".")) {
                name = name.substring(name.indexOf(".") + 1);
            }
            if (name.equals("png") && name.equals("jpeg")) {
                //error type
                return;
            }
            if (file.getSize() > 1000000) {
                //error size
                return;
            }
            File folder = new File(upload_path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            String filename = UUID.randomUUID().toString() + file.getOriginalFilename();
            file.transferTo(new File(upload_path + "\\" + filename));
            user.setAvatar(filename);
            userRepo.save(user);
        }
    }

    public void profileInfo(User user, Model model) {
        if (user.isCurator()) {
            model.addAttribute("projects", teamRepo.findBySupervisor(user));
        } else {
            model.addAttribute("projects", user.getProject_intensive_list());
        }
    }
}
