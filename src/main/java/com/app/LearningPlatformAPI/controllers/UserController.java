package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Data
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getUserProfile(HttpServletRequest request){
        return new ResponseEntity<>(userService.getProfile(request), HttpStatus.OK);
    }

    @PostMapping("/enrollCourse/{courseId}")
    public ResponseEntity<ResponseDto> enrolledCourse(@PathVariable("courseId") Long Id, HttpServletRequest request){
        return new ResponseEntity<>(userService.enrollCourse(request,Id),HttpStatus.CREATED);
    }

    @PostMapping("/completeModule/{courseId}/{moduleId}")
    public ResponseEntity<ResponseDto> completeModule(@PathVariable("moduleId") Long id,@PathVariable("courseId") Long courseId, HttpServletRequest request){
        return new ResponseEntity<>(userService.completeModuleService(request,courseId,id),HttpStatus.OK);
    }

    @GetMapping("/getMyLearning")
    public ResponseEntity<ResponseDto> myLearning(HttpServletRequest request){
        return new ResponseEntity<>(userService.myLearningService(request),HttpStatus.OK);
    }
}
