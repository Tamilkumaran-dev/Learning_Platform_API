package com.app.LearningPlatformAPI.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserDb", description = "Entity representing a user enrolled in courses")
public class UserDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the user", example = "101")
    private Long id;

    @Schema(description = "User's full name", example = "Alice Johnson")
    private String name;

    @Schema(description = "User's email address", example = "alice@example.com")
    private String email;

    @Schema(description = "User's password", example = "user@123")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "enrolledCourse",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "courseId", referencedColumnName = "id")
    )
    @Schema(description = "Courses the user is enrolled in")
    private Set<CourseDb> enrolledCourseList;

    @Schema(description = "Role assigned to the user", example = "ROLE_USER")
    private String role = "ROLE_USER";
}
