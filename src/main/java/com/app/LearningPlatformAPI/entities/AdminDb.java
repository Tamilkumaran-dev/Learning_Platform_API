package com.app.LearningPlatformAPI.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "AdminDb", description = "Entity representing an Admin user and their managed courses")
public class AdminDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the admin", example = "1")
    private Long id;

    @Schema(description = "Admin's full name", example = "John Doe")
    private String name;

    @Schema(description = "Admin's email address", example = "admin@example.com")
    private String email;

    @Schema(description = "Admin's account password", example = "password123")
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "List of courses created by the admin")
    private List<CourseDb> courses;

    @Schema(description = "Role assigned to the admin", example = "ROLE_ADMIN")
    private String role = "ROLE_ADMIN";

    @Schema(description = "Verification status of the admin", example = "UNVERIFIED")
    private String verified = "UNVERIFIED";
}
