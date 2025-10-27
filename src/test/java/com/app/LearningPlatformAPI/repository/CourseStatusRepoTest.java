package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.CourseStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class CourseStatusRepoTest {

    @Autowired
    private CourseStatusRepo courseStatusRepo;

    CourseStatus courseStatus;

    @BeforeEach
    void setUp() {
        courseStatus = new CourseStatus();
        courseStatus.setUserId(1L);
        courseStatus.setCourseId(2L);
        courseStatusRepo.save(courseStatus);
    }

    @AfterEach
    void tearDown() {
        courseStatus = null;
        courseStatusRepo.deleteAll();
    }

    @Test
    void findAllByUserId_Found() {
        //when
        Optional<List<CourseStatus>> courseStatusList = courseStatusRepo.findAllByUserId(1L);
        //then
        Assertions.assertEquals(courseStatusList.get().get(0).getUserId(),1L);
    }

    @Test
    void findAllByUserId_NotFound() {
        //when
        Optional<List<CourseStatus>> courseStatusList = courseStatusRepo.findAllByUserId(2L);
        //then
        Assertions.assertTrue(courseStatusList.get().isEmpty());
    }

    @Test
    void findByUserIdAndCourseId_Found() {
        Optional<CourseStatus> courseStatus = courseStatusRepo.findByUserIdAndCourseId(1L,2L);
        //then
        Assertions.assertEquals(courseStatus.get().getCourseId(),2L);
        Assertions.assertEquals(courseStatus.get().getUserId(),1L);
    }

    @Test
    void findByUserIdAndCourseId_NotFound() {

        Optional<CourseStatus> courseStatus = courseStatusRepo.findByUserIdAndCourseId(1L,3L);
        //then
        Assertions.assertTrue(courseStatus.isEmpty());
    }
}