package com.app.LearningPlatformAPI.services;


import com.app.LearningPlatformAPI.dto.MyLearningDTO;
import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.CourseStatus;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.exception.exceptions.NotFound;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import com.app.LearningPlatformAPI.repository.CourseStatusRepo;
import com.app.LearningPlatformAPI.repository.ModuleRepository;
import com.app.LearningPlatformAPI.repository.UserRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class UserService {

    private UserRepository userRepo;
    private CourseRepository courseRepo;
    private ModuleRepository moduleRepo;
    private CourseStatusRepo courseStatusRepo;
    private JwtUtil jwtUtil;

    public ResponseDto getProfile(HttpServletRequest request){

        String pureToken = "";

        for(Cookie cookie : request.getCookies()){
            if("jwt".equals(cookie.getName())){
                pureToken = cookie.getValue();
            }
        }

        String email =jwtUtil.decodeToken(pureToken).getSubject();
        Optional<UserDb> user = userRepo.findByEmail(email);

        if(user.isEmpty()){
            throw new NotFound("User not found");
        }

        return new ResponseDto("User Profile","Profile",user);

    }

    public ResponseDto enrollCourse(HttpServletRequest request, Long courseId) {

        String pureToken = "";

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                pureToken = cookie.getValue();
            }
        }

        String email = jwtUtil.decodeToken(pureToken).getSubject();
        Optional<UserDb> user = userRepo.findByEmail(email);
        Optional<CourseDb> course = courseRepo.findById(courseId);

        if(user.isEmpty()){
            throw new RuntimeException("The user is not found");
        }
        if(course.isEmpty()){
            throw new RuntimeException("The course is not found");
        }

//        course.get().getEnrolledUsers().add(user.get());
//
//        courseRepo.save(course.get());

        user.get().getEnrolledCourseList().add(course.get());

        userRepo.save(user.get());

        return new ResponseDto("Enroll course successfully", "Enrolled");

    }

    public ResponseDto completeModuleService(HttpServletRequest request,Long courseId, Long id) {

        String pureToken = "";

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                pureToken = cookie.getValue();
            }
        }

        if(pureToken.equals("")){
            throw new RuntimeException("Login first");
        }

        String email = jwtUtil.decodeToken(pureToken).getSubject();
        Optional<UserDb> user = userRepo.findByEmail(email);

        if(user.isEmpty()){
            throw new RuntimeException("This user can not perform this action");
        }

        else {
            Optional<CourseStatus> fetchStatus = courseStatusRepo.findByUserIdAndCourseId(user.get().getId(),courseId);
            if(fetchStatus.isPresent()){
                fetchStatus.get().getModules().add(id);
                courseStatusRepo.save(fetchStatus.get());
            }
            else{
                CourseStatus newStatus = new CourseStatus();
                newStatus.setCourseId(courseId);
                newStatus.setUserId(user.get().getId());

                newStatus.getModules().add(id);
                courseStatusRepo.save(newStatus);

            }


            return new ResponseDto("Module Completed","Completed");
        }
    }

    public ResponseDto myLearningService(HttpServletRequest request) {

        String pureToken = "";
        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                pureToken = cookie.getValue();
            }
        }

        if (pureToken.isEmpty()) {
            throw new RuntimeException("Login first");
        }

        String email = jwtUtil.decodeToken(pureToken).getSubject();
        Optional<UserDb> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException("This user cannot perform this action");
        }

        UserDb currentUser = user.get();

        if (currentUser.getEnrolledCourseList() == null || currentUser.getEnrolledCourseList().isEmpty()) {
            return new ResponseDto("You haven't enrolled in any course", "Not Enrolled");
        }

        List<CourseStatus> statusList = courseStatusRepo.findAllByUserId(currentUser.getId())
                .orElse(new ArrayList<>());

        List<MyLearningDTO> myLearning = new ArrayList<>();

        for (CourseDb course : currentUser.getEnrolledCourseList()) {
            // find matching course status (if any)
            CourseStatus matchedStatus = statusList.stream()
                    .filter(status -> status.getCourseId().equals(course.getId()))
                    .findFirst()
                    .orElse(null);

            MyLearningDTO dto = new MyLearningDTO();
            dto.setCourse(course);
            dto.setCourseStatus(matchedStatus);
            myLearning.add(dto);
        }

        return new ResponseDto("MyLearning", "MyLearning", Optional.of(myLearning));
    }


//    public ResponseDto myLearningService(HttpServletRequest request){
//
//        String pureToken = "";
//
//        for (Cookie cookie : request.getCookies()) {
//            if ("jwt".equals(cookie.getName())) {
//                pureToken = cookie.getValue();
//            }
//        }
//
//        if(pureToken.equals("")){
//            throw new RuntimeException("Login first");
//        }
//
//        String email = jwtUtil.decodeToken(pureToken).getSubject();
//        Optional<UserDb> user = userRepo.findByEmail(email);
//
//        if(user.isEmpty()){
//            throw new RuntimeException("This user can not perform this action");
//        }
//        else{
//            if(!user.get().getEnrolledCourseList().isEmpty()) {
//                Optional<List<CourseStatus>> statusList = courseStatusRepo.findAllByUserId(user.get().getId());
//
//                ArrayList<MyLearningDTO> myLearning = new ArrayList<>();
//
//                for(CourseDb course : user.get().getEnrolledCourseList()){
//                    MyLearningDTO myLearningDTO = new MyLearningDTO();
//                    for(CourseStatus status : statusList.get()){
//                        if(status.getCourseId().equals(course.getId())){
//                            myLearningDTO.setCourse(course);
//                            myLearningDTO.setCourseStatus(status);
//                            myLearning.add(myLearningDTO);
//                            break;
//                        }
//                    }
//                }
//                return new ResponseDto("MyLearning","MyLearning",Optional.of(myLearning));
//            }
//            else{
//                return new ResponseDto("Your haven't enrolled in anycoures","Not Enrolled");
//            }
//        }
//
//    }
}
