package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.TagProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagProjectRepo extends JpaRepository<TagProject, Long> {
    TagProject findByText(String text);
}
