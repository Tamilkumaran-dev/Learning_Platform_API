package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.MasterDb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class MasterRepositoryTest {

    @Autowired
    private MasterRepository masterRepo;

    private MasterDb master;

    @BeforeEach
    void setUp() {
        master = new MasterDb();
        master.setName("Tamil");
        master.setEmail("tamil@gmail.com");
        master.setPassword("Tamil12345");
        masterRepo.save(master);
    }

    @AfterEach
    void tearDown() {
        master = null;
        masterRepo.deleteAll();
    }

    @Test
    void find_Master_By_Email(){

        //when
        Optional<MasterDb> fetchData = masterRepo.findByEmail(master.getEmail());
        //then
        Assertions.assertEquals(master.getEmail(),fetchData.get().getEmail());
    }

    @Test
    void find_Master_By_Email_NotFound(){
        //when
        Optional<MasterDb> fetchData = masterRepo.findByEmail("kumaran@gmail.com");
        //then
        Assertions.assertTrue(fetchData.isEmpty());

    }
}