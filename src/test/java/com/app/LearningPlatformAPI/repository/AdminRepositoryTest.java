package com.app.LearningPlatformAPI.repository;

import com.app.LearningPlatformAPI.entities.AdminDb;
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

@DataJpaTest
@ActiveProfiles("test")
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepo;

    AdminDb admin;

    @BeforeEach
    void setUp(){
        admin = new AdminDb();
        admin.setName("Tamil");
        admin.setEmail("tamil@gmail.com");
        adminRepo.save(admin);
    }

    @AfterEach
    void tearDown() {
        admin = null;
        adminRepo.deleteAll();
    }

    @Test
    void find_By_Email_Test() {

        //when
        Optional<AdminDb> fetchData = adminRepo.findByEmail(admin.getEmail());
        //then
        Assertions.assertEquals(admin.getEmail(),fetchData.get().getEmail());
    }

    @Test
    void verified_Account_Test() {

        //given
        Pageable pageable = PageRequest.of(0,10);
        admin.setVerified("VERIFIED");

        //when
        Page<AdminDb> VerifiedAdmin = adminRepo.unverifiedAccount("VERIFIED",pageable);

        Assertions.assertEquals(admin.getVerified(),VerifiedAdmin.getContent().get(0).getVerified());
    }

    @Test
    void unverified_Account_Test() {

        //given
        Pageable pageable = PageRequest.of(0,10);
        admin.setVerified("VERIFIED");

        //when
        Page<AdminDb> VerifiedAdmin = adminRepo.unverifiedAccount("UNVERIFIED",pageable);

        Assertions.assertTrue(VerifiedAdmin.getContent().isEmpty());
    }
}