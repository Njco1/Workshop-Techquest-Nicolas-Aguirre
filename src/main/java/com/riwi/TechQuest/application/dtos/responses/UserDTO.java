package com.riwi.TechQuest.application.dtos.responses;

import com.riwi.TechQuest.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Role is mandatory")
    private Role role;

    private List<Long> skillIds;
}
