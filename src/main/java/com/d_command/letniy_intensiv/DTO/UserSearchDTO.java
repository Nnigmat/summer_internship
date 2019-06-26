package com.d_command.letniy_intensiv.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDTO {
    private Long id;
    private String username;
    private String role;
    private String error;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
