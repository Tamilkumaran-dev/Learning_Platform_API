package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.CourseDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseDb,Long> {

    @Query("SELECT c FROM CourseDb c " +
            "WHERE LOWER(c.courseTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.courseDes) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<CourseDb> searchCourse(@Param("keyword") String keyword, Pageable pageable);


}
