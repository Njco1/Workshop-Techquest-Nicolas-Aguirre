package com.riwi.TechQuest.application.services.impl;

import com.riwi.TechQuest.application.dtos.requests.LoginRequest;
import com.riwi.TechQuest.domain.entities.User;
import com.riwi.TechQuest.domain.enums.Role;
import com.riwi.TechQuest.infrastructure.helpers.JwtUtil;
import com.riwi.TechQuest.infrastructure.persitence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = (User) userDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtil.generateToken(user);
    }

    public Role getCurrentUserRole() {
        String email = getCurrentAuthenticatedUserEmail();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return ((User) userDetails).getRole(); // Asegúrate de que tu UserDetails devuelva la entidad User
    }

    private String getCurrentAuthenticatedUserEmail() {
        // Obtén el email del usuario autenticado del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    public boolean hasPermission(String permission) {
        Role role = getCurrentUserRole();
        return role.getPermissions().contains(permission);
    }
}

