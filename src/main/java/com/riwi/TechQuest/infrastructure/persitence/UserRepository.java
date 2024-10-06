package com.riwi.TechQuest.infrastructure.persitence;

import com.riwi.TechQuest.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); //Método para buscar por email

    Optional<User> findByName(String name); // Método para buscar por nombre
}
