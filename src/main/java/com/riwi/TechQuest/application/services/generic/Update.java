package com.riwi.TechQuest.application.services.generic;

import org.springframework.http.ResponseEntity;

public interface Update<ID, Entity> {
    public Entity update(ID id, Entity entity);
}
