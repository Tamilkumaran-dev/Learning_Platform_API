package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.ModulesDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModulesDb,Long> {
}
