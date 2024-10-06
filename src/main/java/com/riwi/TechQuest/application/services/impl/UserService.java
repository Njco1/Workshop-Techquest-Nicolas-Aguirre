package com.riwi.TechQuest.application.services.impl;

import com.riwi.TechQuest.application.dtos.responses.UserDTO;
import com.riwi.TechQuest.domain.entities.Skill;
import com.riwi.TechQuest.domain.entities.User;
import com.riwi.TechQuest.domain.ports.service.IUserService;
import com.riwi.TechQuest.infrastructure.persitence.SkillRepository;
import com.riwi.TechQuest.infrastructure.persitence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillRepository skillRepository;

    // Inyección de un servicio para obtener el rol del usuario autenticado
    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<UserDTO> create(UserDTO userDTO) {
        // Verifica si el usuario tiene el rol de ADMIN
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can create users.");
        }

        User user = User.builder()
                .name(userDTO.getName()) // Usa el nombre proporcionado
                .role(userDTO.getRole()) // Usa el rol proporcionado
                .skills(skillRepository.findAllById(userDTO.getSkillIds())) // Usa las habilidades proporcionadas
                .enabled(true) // Habilitado por defecto
                .build();

        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = UserDTO.builder()
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .skillIds(savedUser.getSkills().stream().map(Skill::getId).collect(Collectors.toList()))
                .build();

        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long id) {
        // Verifica si el usuario tiene el rol de ADMIN
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can delete users.");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDTO> readById(Long id) {
        // Cualquier usuario puede leer por ID
        return userRepository.findById(id).map(user -> UserDTO.builder()
                .name(user.getName())
                .role(user.getRole())
                .skillIds(user.getSkills().stream()
                        .map(Skill::getId)
                        .collect(Collectors.toList()))
                .build());
    }

    @Override
    public List<UserDTO> readAll() {
        // Verifica si el usuario tiene el rol de ADMIN o TEACHER
        if (!authService.hasPermission("CAN_MANAGE_ALL") && !authService.hasPermission("CAN_VIEW_MISSIONS")) {
            throw new RuntimeException("Unauthorized: Only ADMIN and TEACHER can read users.");
        }

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserDTO.builder()
                        .name(user.getName())
                        .role(user.getRole())
                        .skillIds(user.getSkills().stream().map(Skill::getId).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        // Verifica si el usuario tiene el rol de ADMIN
        if (!authService.hasPermission("CAN_MANAGE_ALL")) {
            throw new RuntimeException("Unauthorized: Only ADMIN can update users.");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setRole(userDTO.getRole());
        existingUser.setSkills(skillRepository.findAllById(userDTO.getSkillIds()));

        userRepository.save(existingUser);

        return UserDTO.builder()
                .name(existingUser.getName())
                .role(existingUser.getRole())
                .skillIds(existingUser.getSkills().stream().map(Skill::getId).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ResponseEntity<UserDTO> create() {
        return null;
    }
}



