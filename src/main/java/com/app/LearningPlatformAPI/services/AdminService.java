package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.ModulesDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.exception.exceptions.NotFound;
import com.app.LearningPlatformAPI.repository.AdminRepository;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import com.app.LearningPlatformAPI.repository.ModuleRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AdminService {

    private CourseRepository courseRepo;
    private AdminRepository adminRepo;
    private ModuleRepository moduleRepo;
    private JwtUtil jwtUtil;

    public ResponseDto addCourse(CourseDb courseDb, HttpServletRequest request){

        String pureToken = "";

        for(Cookie cookie : request.getCookies()){
            if("jwt".equals(cookie.getName())){
                pureToken = cookie.getValue();
            }
        }

        String email =jwtUtil.decodeToken(pureToken).getSubject();

        Optional<AdminDb> adminDb = adminRepo.findByEmail(email);


        courseDb.setAdmin(adminDb.get());
        courseDb.setTime(LocalDateTime.now());

        CourseDb course = courseRepo.save(courseDb);

        return new ResponseDto("The course has been created","Created",Optional.of(course));

    }


    public ResponseDto addModulesIntoTheCourse(Long id, ModulesDb modulesDb){
        CourseDb course = courseRepo.findById(id).get();

        modulesDb.setCourse(course);

        moduleRepo.save(modulesDb);

        return new ResponseDto("Module is added","Add");
    }



    public ResponseDto getAdminProfile(HttpServletRequest request){


        String pureToken = "";

        for(Cookie cookie : request.getCookies()){
            if("jwt".equals(cookie.getName())){
                pureToken = cookie.getValue();
            }
        }

        String email =jwtUtil.decodeToken(pureToken).getSubject();

        Optional<AdminDb> adminDb = adminRepo.findByEmail(email);
        if(adminDb.isEmpty()){
            throw new NotFound("Admin not found");
        }
        return new ResponseDto("Admin profile","Profile",Optional.of(adminDb));
    }


    public ResponseDto updateCourse(Long id,CourseDb editedCourse){

        CourseDb course = courseRepo.findById(id).get();

        course.setCourseTitle(editedCourse.getCourseTitle());
        course.setCourseDes(editedCourse.getCourseDes());

        courseRepo.save(course);

        return new ResponseDto("Course updated","Updated");

    }


    public ResponseDto UpdateModule(Long id,ModulesDb editedModule){
        ModulesDb module = moduleRepo.findById(id).get();

        module.setModuleTitle(editedModule.getModuleTitle());
        module.setModuleDes(editedModule.getModuleDes());
        module.setContent(editedModule.getContent());
        module.setTime(LocalDateTime.now());

        moduleRepo.save(module);

        return new ResponseDto("module Updated","Update");

    }


    public ResponseDto dropModule(Long id){

        moduleRepo.deleteById(id);
        return new ResponseDto("Module deleted successfully","Deleted");

    }

    public ResponseDto dropCourse(Long id){

        CourseDb course = courseRepo.findById(id).get();

        for (UserDb user : course.getEnrolledUsers()) {
            user.getEnrolledCourseList().remove(course);
        }

        courseRepo.delete(course);

        return new ResponseDto("Course deleted successfully","Deleted");
    }



}
