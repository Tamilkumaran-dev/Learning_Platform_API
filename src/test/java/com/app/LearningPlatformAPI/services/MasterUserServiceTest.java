package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.MasterDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.repository.AdminRepository;
import com.app.LearningPlatformAPI.repository.MasterRepository;
import com.app.LearningPlatformAPI.repository.UserRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MasterUserServiceTest {


    @Mock
    private UserRepository userRepo;

    @Mock
    private AdminRepository adminRepo;

    @Mock
    private MasterRepository masterRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdminService adminService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private MasterUserService masterUserService;

    private MasterDb master;
    private AdminDb admin;
    private UserDb user;

    @BeforeEach
    void setUp() {

        master=new MasterDb();
        master.setId(1L);
        master.setName("Tamil");
        master.setEmail("tamil@gmailcom");
        master.setPassword("tamil");


        admin = new AdminDb();
        admin.setId(2L);
        admin.setName("Kumaran");
        admin.setEmail("Kumaran@gmail.com");
        admin.setPassword("tamil");

        user = new UserDb();
        user.setId(3L);
        user.setName("harish");
        user.setEmail("Harish@gmail.com");
        user.setPassword("tamil");

    }

    @Test
    void signUpMaster() {

        Mockito.when(userRepo.findByEmail(master.getEmail())).thenReturn(Optional.empty());
        Mockito.when(adminRepo.findByEmail(master.getEmail())).thenReturn(Optional.empty());
        Mockito.when(masterRepo.findByEmail(master.getEmail())).thenReturn(Optional.empty());

        Mockito.when(passwordEncoder.encode(master.getPassword())).thenReturn("Tamil");

        ResponseDto res = masterUserService.signUpMaster(master);

        Assertions.assertEquals("Registered",res.getResKeyword());


    }

    @Test
    void getMasterProfile() {

        String pureToken = "jwt-token";

        Cookie cookie = new Cookie("jwt","jwt-token");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(jwtUtil.decodeToken(pureToken)).thenReturn(claims);
        Mockito.when(claims.getSubject()).thenReturn("tamil@gmail.com");

        Mockito.when(masterRepo.findByEmail("tamil@gmail.com")).thenReturn(Optional.of(master));

        ResponseDto response = masterUserService.getMasterProfile(request);

        Assertions.assertTrue(response.getData().isPresent());


    }

    @Test
    void getUnVerifiedAdmin() {

        Page<AdminDb> adminList = new PageImpl<>(List.of(admin));
        Pageable pageable = PageRequest.of(0, 5);
        String keyword = "AllAdmin";

        Mockito.when(adminRepo.findAll(pageable)).thenReturn(adminList);

        // When
        Page<AdminDb> res = masterUserService.getUnVerifiedAdmin(keyword, 1, 5);

        // Then
        Assertions.assertEquals(1, res.getContent().size());

    }

    @Test
    void getAdmin() {

        Mockito.when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

        ResponseDto response = masterUserService.getAdmin(1L);

        Assertions.assertEquals(admin.getId(), ((AdminDb) ((Optional<?>) response.getData()).get()).getId());

    }

}