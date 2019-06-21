package com.d_command.letniy_intensiv.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, MODERATOR, CURATOR, BAN;

    @Override
    public String getAuthority() {
        return name();
    }
}
