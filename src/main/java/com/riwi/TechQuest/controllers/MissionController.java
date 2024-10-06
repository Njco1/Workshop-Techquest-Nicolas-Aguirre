package com.riwi.TechQuest.controllers;

import com.riwi.TechQuest.domain.entities.Mission;
import com.riwi.TechQuest.domain.ports.service.IMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private IMissionService missionService;

    @PostMapping("/create")
    public ResponseEntity<Mission> create() {
        return missionService.create();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        missionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/readById/{id}")
    public ResponseEntity<Mission> readById(@PathVariable Long id) {
        return missionService.readById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Mission>> readAll() {
        List<Mission> missions = missionService.readAll();
        return ResponseEntity.ok(missions);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Mission> update(@PathVariable Long id, @RequestBody Mission mission) {
        Mission updatedMission = missionService.update(id, mission);
        return ResponseEntity.ok(updatedMission);
    }
}
