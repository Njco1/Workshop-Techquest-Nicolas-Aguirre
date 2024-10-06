package com.riwi.TechQuest.domain.ports.service;

import com.riwi.TechQuest.application.dtos.responses.UserDTO;
import com.riwi.TechQuest.application.services.generic.*;
import com.riwi.TechQuest.domain.entities.User;
import org.springframework.http.ResponseEntity;

public interface IUserService extends
        Create<UserDTO>,
        Update<Long, UserDTO>,
        Delete<Long>,
        ReadById<UserDTO, Long>,
        ReadAll<UserDTO> {
    }
