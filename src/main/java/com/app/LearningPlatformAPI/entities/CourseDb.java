package com.app.LearningPlatformAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CourseDb", description = "Entity representing a course created by an admin")
public class CourseDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the course", example = "10")
    private Long id;

    @Schema(description = "Title of the course", example = "Spring Boot for Beginners")
    private String courseTitle;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the course")
    private String courseDes;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(description = "Modules included in the course")
    private List<ModulesDb> modules;

    @Schema(description = "Date and time when the course was created")
    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseCreate", referencedColumnName = "id")
    @JsonBackReference
    @Schema(description = "Admin who created this course")
    private AdminDb admin;

    @ManyToMany(mappedBy = "enrolledCourseList")
    @JsonIgnore
    @Schema(description = "List of users enrolled in this course")
    private List<UserDb> enrolledUsers;
}
