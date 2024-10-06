package com.riwi.TechQuest.infrastructure.persitence;

import com.riwi.TechQuest.domain.entities.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
