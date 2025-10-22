package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDb,Long> {

    Optional<UserDb> findByEmail(String email);

}
