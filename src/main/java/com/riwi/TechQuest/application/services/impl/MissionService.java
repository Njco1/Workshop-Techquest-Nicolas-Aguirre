package com.riwi.TechQuest.application.services.impl;

import com.riwi.TechQuest.domain.entities.Mission;
import com.riwi.TechQuest.domain.entities.User;
import com.riwi.TechQuest.domain.enums.Difficulty;
import com.riwi.TechQuest.domain.ports.service.IMissionService;
import com.riwi.TechQuest.infrastructure.persitence.MissionRepository;
import com.riwi.TechQuest.infrastructure.persitence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionService implements IMissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<Mission> create() {
        // Verifica si el usuario tiene el rol de ADMIN
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can create missions.");
        }

        Mission mission = Mission.builder()
                .description("This is a default mission description.") // Descripción por defecto
                .difficulty(Difficulty.MEDIUM) // Dificultad por defecto
                .asignedStudents(List.of()) // Sin estudiantes asignados por defecto
                .build();

        Mission savedMission = missionRepository.save(mission);
        return new ResponseEntity<>(savedMission, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long id) {
        // Verifica si el usuario tiene el rol de ADMIN
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can delete missions.");
        }
        missionRepository.deleteById(id);
    }

    @Override
    public Optional<Mission> readById(Long id) {
        return missionRepository.findById(id);
    }

    @Override
    public List<Mission> readAll() {
        return missionRepository.findAll();
    }

    @Override
    public Mission update(Long id, Mission mission) {
        // Verifica si el usuario tiene el rol de ADMIN
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can update missions.");
        }

        Mission existingMission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        existingMission.setDescription(mission.getDescription());
        existingMission.setDifficulty(mission.getDifficulty());
        existingMission.setAsignedStudents(mission.getAsignedStudents());

        return missionRepository.save(existingMission);
    }
}
