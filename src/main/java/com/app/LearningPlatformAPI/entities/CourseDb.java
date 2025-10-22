package com.app.LearningPlatformAPI.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseTitle;

    @Column(columnDefinition = "TEXT")
    private String courseDes;


    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ModulesDb> modules;

    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseCreate", referencedColumnName = "id")
    @JsonBackReference   // ✅ prevents infinite recursion
    private AdminDb admin;

    @ManyToMany(mappedBy = "enrolledCourseList")
    @JsonIgnore
    private List<UserDb> enrolledUsers;
}
