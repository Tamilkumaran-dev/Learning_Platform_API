package com.app.LearningPlatformAPI.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "ModulesDb", description = "Entity representing an individual module within a course")
public class ModulesDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the module", example = "7")
    private Long id;

    @Schema(description = "Title of the module", example = "Introduction to Spring Boot")
    private String moduleTitle;

    @Lob
    @Schema(description = "Description of the module")
    private String moduleDes;

    @Lob
    @Schema(description = "Main content of the module")
    private String content;

    @Schema(description = "Timestamp of when the module was created")
    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course", referencedColumnName = "id")
    @JsonIgnore
    @Schema(description = "Course this module belongs to")
    private CourseDb course;
}
