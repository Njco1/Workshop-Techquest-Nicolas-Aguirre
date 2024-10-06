package com.riwi.TechQuest.application.services.impl;

import com.riwi.TechQuest.domain.entities.Skill;
import com.riwi.TechQuest.domain.ports.service.ISkillService;
import com.riwi.TechQuest.infrastructure.persitence.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService implements ISkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<Skill> create() {
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can create skills.");
        }

        Skill skill = Skill.builder()
                .name("Default Skill")
                .build();

        Skill savedSkill = skillRepository.save(skill);
        return new ResponseEntity<>(savedSkill, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long id) {
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can delete skills.");
        }
        skillRepository.deleteById(id);
    }

    @Override
    public Optional<Skill> readById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public List<Skill> readAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill update(Long id, Skill skill) {
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can update skills.");
        }

        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        existingSkill.setName(skill.getName());

        return skillRepository.save(existingSkill);
    }
}

