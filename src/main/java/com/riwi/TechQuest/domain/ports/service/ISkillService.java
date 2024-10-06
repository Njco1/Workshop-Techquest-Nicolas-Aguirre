package com.riwi.TechQuest.domain.ports.service;

import com.riwi.TechQuest.application.services.generic.*;
import com.riwi.TechQuest.domain.entities.Skill;
import org.springframework.http.ResponseEntity;

public interface ISkillService extends
        Create<Skill>,
        Update<Long, Skill>,
        Delete<Long>,
        ReadById<Skill, Long>,
        ReadAll<Skill> {
}

