package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.UserDb;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    UserDb user;

    @BeforeEach
    void setUp() {
        user = new UserDb();
        user.setEmail("tamil@gmail.com");
        userRepo.save(user);

    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepo.deleteAll();
    }

    @Test
    void findByEmail_Found() {

        //when
        Optional<UserDb>  fetchData = userRepo.findByEmail(user.getEmail());
        //then
        assertEquals(user.getEmail(),fetchData.get().getEmail());
    }

    @Test
    void findByEmail_NotFound() {
        //when
        Optional<UserDb>  fetchData = userRepo.findByEmail("kumaran@gmail.com");
        //then
        assertTrue(fetchData.isEmpty());
    }
}