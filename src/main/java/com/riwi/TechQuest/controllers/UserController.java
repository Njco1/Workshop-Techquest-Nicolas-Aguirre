package com.riwi.TechQuest.controllers;

import com.riwi.TechQuest.application.dtos.responses.UserDTO;
import com.riwi.TechQuest.domain.ports.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create() {
        return userService.create();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/readById/{id}")
    public ResponseEntity<UserDTO> readById(@PathVariable Long id) {
        return userService.readById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<UserDTO> readAll() {
        return userService.readAll();
    }

    @PutMapping("/update/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }
}
