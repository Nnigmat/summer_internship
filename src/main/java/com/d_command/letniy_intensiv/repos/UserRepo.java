package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
