package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.CourseDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminDb,Long> {

    public Optional<AdminDb> findByEmail(String email);

    @Query("SELECT a FROM AdminDb a " +
            "WHERE LOWER(a.verified) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<AdminDb> unverifiedAccount(@Param("keyword") String keyword, Pageable pageable);

}
