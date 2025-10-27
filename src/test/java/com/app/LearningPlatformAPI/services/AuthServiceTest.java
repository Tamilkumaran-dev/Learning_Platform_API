package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.LoginDto;
import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.MasterDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.repository.AdminRepository;
import com.app.LearningPlatformAPI.repository.MasterRepository;
import com.app.LearningPlatformAPI.repository.UserRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Claims claims;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MasterRepository masterRepo;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthService authService;


    @Test
    void signUp() {

        // given
        UserDb userDb = new UserDb();
        userDb.setEmail("Tamil@gmail.com");
        userDb.setPassword("tamil");

        // Mock empty Optional for all repositories
        Mockito.when(userRepo.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());
        Mockito.when(masterRepo.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());

        // Mock password encoding
        Mockito.when(passwordEncoder.encode(userDb.getPassword())).thenReturn("encodedPassword");

        // Mock saving
        Mockito.when(userRepo.save(Mockito.any(UserDb.class))).thenReturn(userDb);

        // when
        ResponseDto response = authService.SignUp(userDb);

        // then
        Assertions.assertEquals("Register Successfully", response.getMessage());
        Assertions.assertEquals("Register", response.getResKeyword());

    }

    @Test
    void signIn() {

        //given
        UserDb userDb = new UserDb();
        userDb.setEmail("Tamil@gmail.com");
        userDb.setPassword("tamil");
        userDb.setRole("ROLE_USER");

        LoginDto login = new LoginDto();
        login.setEmail("Tamil@gmail.com");
        login.setPassword("tamil");

        ResponseDto responseDto = new ResponseDto("Login Successful", "ROLE_USER", Optional.ofNullable("token"));

        // Mock empty Optional for all repositories
        Mockito.when(userRepo.findByEmail(userDb.getEmail())).thenReturn(Optional.of(userDb));
        Mockito.when(adminRepository.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());
        Mockito.when(masterRepo.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());

        Mockito.when(passwordEncoder.matches(login.getPassword(), userDb.getPassword())).thenReturn(true);
        Mockito.when(jwtUtil.generateJwtToken(login.getEmail())).thenReturn("token");

        //when
        ResponseDto response = authService.SignIn(login);

        //then
        Assertions.assertEquals(responseDto,response);

    }



    @Test
    void registerAdmin() {

        //given
        AdminDb adminDb = new AdminDb();
        adminDb.setEmail("tamil@gamil.com");
        adminDb.setPassword("Tamil");
        adminDb.setRole("ROLE_ADMIN");

        ResponseDto responseDto = new ResponseDto("Register admin successfully","Register");

        Mockito.when(adminRepository.findByEmail(adminDb.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepo.findByEmail(adminDb.getEmail())).thenReturn(Optional.empty());

        //when
        ResponseDto response = authService.RegisterAdmin(adminDb);

        //then
        Assertions.assertEquals(responseDto,response);



    }

    @Test
    void isLoggedIn() {

        //given
        UserDb userDb = new UserDb();
        userDb.setEmail("tamil@gmail.com");
        userDb.setRole("ROLE_USER");

        String pureToken = "fake-jwt-token";

        Cookie jwtCookie = new Cookie("jwt", "fake-jwt-token");

        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});

        Claims claims1 = Mockito.mock(Claims.class);

        Mockito.when(jwtUtil.decodeToken("fake-jwt-token")).thenReturn(claims1);
        Mockito.when(claims1.getSubject()).thenReturn("tamil@gmail.com");

        Mockito.when(userRepo.findByEmail(userDb.getEmail())).thenReturn(Optional.of(userDb));
        Mockito.when(adminRepository.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());
        Mockito.when(masterRepo.findByEmail(userDb.getEmail())).thenReturn(Optional.empty());

        //when
        ResponseDto login = authService.isLoggedIn(request);

        Assertions.assertEquals("ROLE_USER",login.getData().get());

    }
}