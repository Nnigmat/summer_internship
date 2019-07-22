package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.domain.Project;
import com.d_command.letniy_intensiv.domain.Team;
import com.d_command.letniy_intensiv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepo extends JpaRepository<Team, Long> {
    List<Team> findByProject(Project project);

    List<Team> findByIntensive(Intensive intensive);

    List<Team> findBySupervisor(User user);

    Team findByProjectAndIntensive(Project project, Intensive intensive);
}
