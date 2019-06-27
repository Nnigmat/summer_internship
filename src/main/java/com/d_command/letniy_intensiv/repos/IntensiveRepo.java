package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.Intensive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntensiveRepo extends JpaRepository<Intensive, Long> {
    List<Intensive> findByName(String name);
}
