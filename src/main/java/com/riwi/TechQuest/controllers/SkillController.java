package com.riwi.TechQuest.controllers;

import com.riwi.TechQuest.domain.entities.Skill;
import com.riwi.TechQuest.domain.ports.service.ISkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills") // Base URL para las habilidades
public class SkillController {

    @Autowired
    private ISkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<Skill> create() {
        return skillService.create(); // Llama al servicio para crear la habilidad
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/readById/{id}")
    public ResponseEntity<Skill> readById(@PathVariable Long id) {
        return skillService.readById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Skill>> readAll() {
        List<Skill> skills = skillService.readAll();
        return ResponseEntity.ok(skills);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Skill> update(@PathVariable Long id, @RequestBody Skill skill) {
        Skill updatedSkill = skillService.update(id, skill);
        return ResponseEntity.ok(updatedSkill);
    }
}

