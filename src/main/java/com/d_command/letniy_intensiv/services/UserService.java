package com.d_command.letniy_intensiv.services;

import com.d_command.letniy_intensiv.DTO.UserSearchDTO;
import com.d_command.letniy_intensiv.ModelMapper.UserMapper;
import com.d_command.letniy_intensiv.domain.RegID;
import com.d_command.letniy_intensiv.domain.Role;
import com.d_command.letniy_intensiv.domain.TagUser;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.RegIDRepo;
import com.d_command.letniy_intensiv.repos.TagUserRepo;
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

    private final UserRepo userRepo;
    private final TeamRepo teamRepo;
    private final TagUserRepo tagRepo;

    @Autowired
    public UserService(UserRepo userRepo, TeamRepo teamRepo, TagUserRepo tagRepo) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
        this.tagRepo = tagRepo;
    }

    @Autowired
    private RegIDRepo idRepo;

    @Value("${upload.path}")
    private String upload_path;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepo.findByUsername(username);
    }

    public User findByUsername(String username) { return userRepo.findByUsername(username); }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void create(String username, String password, String name, String surname) {
        if (userRepo.findByUsername(username) == null && !username.equals("")) {
            userRepo.save(new User(username, password, name, surname));
        }
    }

    public LinkedList<UserSearchDTO> searchUser(String username) {
        if (username.equals("")) {
            return UserMapper.userToUserSearchDTO(userRepo.findAll());
        } else {
            if (username.charAt(0) == '#') {
                if (tagRepo.findByText(username.substring(1)) == null) {
                    LinkedList<UserSearchDTO> usersDTO = new LinkedList<>();
                    UserSearchDTO temp = new UserSearchDTO();
                    temp.setError("Such tag doesn't exist");
                    usersDTO.add(temp);
                    return usersDTO;
                } else {
                    List<User> users = userRepo.findAll();
                    List<User> temp = userRepo.findAll();
                    for (User item : temp) {
                        if (!item.getTags().contains(tagRepo.findByText(username.substring(1)))) {
                            users.remove(item);
                        }
                    }
                    if (users.isEmpty()) {
                        LinkedList<UserSearchDTO> usersDTO = new LinkedList<>();
                        UserSearchDTO temp2 = new UserSearchDTO();
                        temp2.setError("No users with such tag");
                        usersDTO.add(temp2);
                        return usersDTO;
                    } else {
                        return UserMapper.userToUserSearchDTO(users);
                    }
                }
            } else {
                if (userRepo.findByUsername(username) != null) {
                    LinkedList<User> temp = new LinkedList<>();
                    temp.add(userRepo.findByUsername(username));
                    return UserMapper.userToUserSearchDTO(temp);
                } else {
                    LinkedList<UserSearchDTO> usersDTO = new LinkedList<>();
                    UserSearchDTO temp = new UserSearchDTO();
                    temp.setError("Such user doesn't exist");
                    usersDTO.add(temp);
                    return usersDTO;
                }
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
            if (!name.equals("png") && !name.equals("jpeg") && !name.equals("jpg")) {
                //error type
                return;
            }
            if (file.getSize() > 1048576) {
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

    public void updateLink() {
        idRepo.delete(idRepo.findAll().get(0));
        idRepo.save(new RegID());
    }

    public void profileInfo(User user, Model model) {
        if (user.isCurator()) {
            model.addAttribute("projects", teamRepo.findBySupervisor(user));
        } else {
            model.addAttribute("projects", user.getProject_intensive_list());
        }
        model.addAttribute("tags", tagRepo.findAll());
    }

    public UUID getInvLink() {
        return idRepo.findAll().get(0).getId();
    }

    public void addTag(String tag, User user) {
        if (!user.containsTag(tag)) {
            ;
            user.addTag(tagRepo.findByText(tag));
            userRepo.save(user);
        }
    }
}
