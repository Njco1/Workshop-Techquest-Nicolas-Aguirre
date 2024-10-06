package com.riwi.TechQuest.domain.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public enum Role {
    ADMIN(Set.of("CAN_MANAGE_ALL")),
    TEACHER(Set.of("CAN_MANAGE_CLASSES", "CAN_ASSIGN_MISSIONS")),
    STUDENT(Set.of("CAN_VIEW_MISSIONS"));

    private final Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.name()));
    }
}
