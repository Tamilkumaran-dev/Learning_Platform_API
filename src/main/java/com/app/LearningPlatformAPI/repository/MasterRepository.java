package com.app.LearningPlatformAPI.repository;


import com.app.LearningPlatformAPI.entities.MasterDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<MasterDb, Long> {

    public Optional<MasterDb> findByEmail(String email);


}
