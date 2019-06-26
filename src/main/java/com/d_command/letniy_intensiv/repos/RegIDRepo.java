package com.d_command.letniy_intensiv.repos;

import com.d_command.letniy_intensiv.domain.RegID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegIDRepo extends JpaRepository<RegID, UUID> {

}
