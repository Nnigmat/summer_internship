package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.TagUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagUserRepo extends JpaRepository<TagUser, Long> {
    TagUser findByText(String text);
}
