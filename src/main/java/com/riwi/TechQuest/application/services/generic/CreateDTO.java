package com.riwi.TechQuest.application.services.generic;

import org.springframework.http.ResponseEntity;

public interface CreateDTO<Entity, EntityRequest> {
    public ResponseEntity<Entity> createDTO(EntityRequest entityRequest);
}
