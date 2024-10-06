package com.riwi.TechQuest.infrastructure.persitence;

import com.riwi.TechQuest.domain.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
