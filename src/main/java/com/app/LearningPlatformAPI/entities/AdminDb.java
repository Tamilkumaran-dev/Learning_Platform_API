package com.app.LearningPlatformAPI.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference  // ✅ helps control serialization direction
    private List<CourseDb> courses;

    private String role = "ROLE_ADMIN";

    private String verified = "UNVERIFIED";
}
