package com.riwi.TechQuest.domain.ports.service;

import com.riwi.TechQuest.application.services.generic.*;
import com.riwi.TechQuest.domain.entities.Mission;

public interface IMissionService extends
        Create<Mission>,
        Update<Long, Mission>,
        Delete<Long>,
        ReadById<Mission, Long>,
        ReadAll<Mission> {
}

