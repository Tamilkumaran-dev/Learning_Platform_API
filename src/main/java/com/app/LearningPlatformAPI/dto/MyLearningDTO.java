package com.app.LearningPlatformAPI.dto;

import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyLearningDTO {

    private CourseDb course;
    private CourseStatus courseStatus;

}
