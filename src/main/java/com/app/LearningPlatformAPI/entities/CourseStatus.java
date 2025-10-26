package com.app.LearningPlatformAPI.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CourseStatus", description = "Tracks user's progress through modules of a course")
public class CourseStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the course status record", example = "100")
    private Long id;

    @Schema(description = "Associated course ID", example = "5")
    private Long courseId;

    @ElementCollection
    @Schema(description = "List of module IDs completed by the user")
    private List<Long> modules = new ArrayList<>();

    @Schema(description = "User ID associated with this course status", example = "12")
    private Long userId;
}
