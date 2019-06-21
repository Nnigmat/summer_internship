package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
