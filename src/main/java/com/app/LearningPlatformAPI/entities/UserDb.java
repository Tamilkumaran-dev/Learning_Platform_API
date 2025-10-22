package com.app.LearningPlatformAPI.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "enrolledCourse", joinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "courseId", referencedColumnName = "id"))
    private Set<CourseDb> enrolledCourseList;

    private String role = "ROLE_USER";


}
