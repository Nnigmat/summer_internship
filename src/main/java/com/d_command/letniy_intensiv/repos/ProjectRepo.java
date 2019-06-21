package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    Project findByName(String name);
    List<Project> findByType(ProjectType type);
}
