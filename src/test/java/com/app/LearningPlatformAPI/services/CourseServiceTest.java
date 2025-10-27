package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.ModulesDb;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import com.app.LearningPlatformAPI.repository.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepo;
    @Mock
    private ModuleRepository moduleRepo;
    @InjectMocks
    private CourseService courseService;

    private CourseDb course;
    private ModulesDb module;

    @BeforeEach
    void setUp() {

        course = new CourseDb();

        course.setCourseTitle("Html");
        course.setCourseDes("Html basic");
        courseRepo.save(course);

        module = new ModulesDb();
        module.setModuleTitle("html into");
        module.setContent("html introduction");
        moduleRepo.save(module);

    }

    @Test
    void getCourse() {


        Mockito.when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        ResponseDto response = courseService.getCourse(1L);

        Assertions.assertTrue(response.getData().isPresent());

    }

    @Test
    void getModule() {

        Mockito.when(moduleRepo.findById(1L)).thenReturn(Optional.of(module));

        ResponseDto response = courseService.getModule(1L);

        Assertions.assertTrue(response.getData().isPresent());


    }
}