package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.ModulesDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.repository.AdminRepository;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import com.app.LearningPlatformAPI.repository.ModuleRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private CourseRepository courseRepo;
    @Mock
    private AdminRepository adminRepo;
    @Mock
    private ModuleRepository moduleRepo;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AdminService adminService;

    private AdminDb admin;

    private CourseDb course;

    private ModulesDb module;

    private UserDb user;

    @BeforeEach
    void setUp() {



        admin = new AdminDb();
        admin.setEmail("tamil@gmail.com");

        module = new ModulesDb();
        module.setModuleTitle("basic Html");
        module.setModuleDes("basic Html Des");
        module.setContent("html");

        user = new UserDb();
        user.setEmail("tami@gmail.com");
        user.setName("Tamil");


        course = new CourseDb();
        course.setCourseTitle("Html");
        course.setCourseDes("html basic");
        course.setEnrolledUsers(new ArrayList<>(List.of(user)));


        AdminDb savedAdmin = adminRepo.save(admin);

        course.setAdmin(savedAdmin);

        courseRepo.save(course);


    }

    @Test
    void addCourse() {

        String pureToken = "jwt-token";

        Cookie cookie = new Cookie("jwt","jwt-token");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(jwtUtil.decodeToken(pureToken)).thenReturn(claims);
        Mockito.when(claims.getSubject()).thenReturn("tamil@gmail.com");
        Mockito.when(adminRepo.findByEmail("tamil@gmail.com")).thenReturn(Optional.of(admin));

        Mockito.when(courseRepo.save(course)).thenReturn(course);

        ResponseDto response = new ResponseDto("The course has been created","Created",Optional.of(course));

        ResponseDto responseTest = adminService.addCourse(course,request);

        Assertions.assertEquals(course,responseTest.getData().get());

    }


    @Test
    void getAdminProfile() {

        String pureToken = "jwt-token";

        Cookie cookie = new Cookie("jwt","jwt-token");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(jwtUtil.decodeToken(pureToken)).thenReturn(claims);
        Mockito.when(claims.getSubject()).thenReturn("tamil@gmail.com");
        Mockito.when(adminRepo.findByEmail("tamil@gmail.com")).thenReturn(Optional.of(admin));

        ResponseDto response = adminService.getAdminProfile(request);

        Assertions.assertEquals(Optional.of(admin),response.getData().get());

    }

    @Test
    void updateCourse() {

        Long courseId= 1L;
        Mockito.when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(courseRepo.save(course)).thenReturn(course);

        ResponseDto response = adminService.updateCourse(courseId,course);

        Assertions.assertEquals("Updated",response.getResKeyword());


    }

    @Test
    void updateModule() {

        Long moduleId = 1L;
        Mockito.when(moduleRepo.findById(moduleId)).thenReturn(Optional.of(module));
        Mockito.when(moduleRepo.save(module)).thenReturn(module);

        ResponseDto res = adminService.UpdateModule(moduleId,module);

        Assertions.assertEquals("Update",res.getResKeyword());

    }

    @Test
    void dropModule() {

        ResponseDto res = adminService.dropModule(1L);

        Assertions.assertEquals("Deleted",res.getResKeyword());

    }

}