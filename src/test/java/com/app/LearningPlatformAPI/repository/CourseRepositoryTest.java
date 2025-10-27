package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.CourseDb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepo;

    CourseDb course;


    @BeforeEach
    void setUp() {
        course = new CourseDb();
        course.setCourseTitle("Html basic");
        course.setCourseDes("This course is for beginner");
        courseRepo.save(course);
    }

    @AfterEach
    void tearDown() {
        course = null;
        courseRepo.deleteAll();
    }

    @Test
    void search_Course_Found() {

        //given
        Pageable pageable = PageRequest.of(0,10);

//        when
        Page<CourseDb> fetchData = courseRepo.searchCourse("Html",pageable);

//        then
        Assertions.assertTrue(fetchData.getContent().get(0).getCourseTitle().toLowerCase().contains("html") ||
                fetchData.getContent().get(0).getCourseDes().toLowerCase().contains("html"));
    }

    @Test
    void search_Course_NotFound() {

        //given
        Pageable pageable = PageRequest.of(0,10);

//        when
        Page<CourseDb> fetchData = courseRepo.searchCourse("CSS",pageable);

//        then
        Assertions.assertTrue(fetchData.getContent().isEmpty());
    }
}