package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {

    @Mock
    private CourseRepository courseRepo;

    @InjectMocks
    private HomeService homeService;


    private CourseDb course1;
    private CourseDb course2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course1 = new CourseDb();
        course1.setId(1L);
        course1.setCourseTitle("Java Basics");
        course1.setCourseDes("java");

        course2 = new CourseDb();
        course2.setId(2L);
        course2.setCourseTitle("Spring Boot Guide");
        course2.setCourseDes("spring boot");

        pageable = PageRequest.of(0, 5);
    }


    @Test
    void searchCourse() {

        Page<CourseDb> courses = new PageImpl<>(List.of(course1,course2));
        Mockito.when(courseRepo.findAll(pageable)).thenReturn(courses);

        Page<CourseDb> response = homeService.searchCourse("allCourse",1,5);

        Assertions.assertEquals(2,response.getContent().size());

    }
}