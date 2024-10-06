package com.riwi.TechQuest.domain.entities;

import com.riwi.TechQuest.domain.enums.Difficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;

@Entity(name = "missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Mission extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mission_students",
            joinColumns = @JoinColumn(name = "mission_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<User> asignedStudents;
}
