package com.d_command.letniy_intensiv.ModelMapper;

import com.d_command.letniy_intensiv.DTO.UserSearchDTO;
import com.d_command.letniy_intensiv.domain.User;

import java.util.LinkedList;
import java.util.List;

public class UserMapper {
    public static LinkedList<UserSearchDTO> userToUserSearchDTO(List<User> users) {
        LinkedList<UserSearchDTO> usersDTO = new LinkedList<>();
        for (User item : users) {
            UserSearchDTO temp = new UserSearchDTO();
            temp.setUsername(item.getUsername());
            temp.setRole(item.getRoles().toString());
            temp.setRole(temp.getRole().substring(1, temp.getRole().indexOf("]")));
            temp.setId(item.getId());
            usersDTO.add(temp);
        }
        return usersDTO;
    }
}
