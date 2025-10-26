package com.app.LearningPlatformAPI.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MasterDb", description = "Entity representing a master-level administrator")
public class MasterDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID for the master admin", example = "1")
    private Long id;

    @Schema(description = "Master admin's full name", example = "System Master")
    private String name;

    @Schema(description = "Master admin's email", example = "master@example.com")
    private String email;

    @Schema(description = "Password for the master admin account")
    private String password;

    @Schema(description = "Role assigned to this user", example = "ROLE_MASTER")
    private String role = "ROLE_MASTER";
}
