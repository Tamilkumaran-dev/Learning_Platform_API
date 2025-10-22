package com.app.LearningPlatformAPI.controllers;


import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.ModulesDb;
import com.app.LearningPlatformAPI.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getProfile(HttpServletRequest request){
        return new ResponseEntity<>(adminService.getAdminProfile(request),HttpStatus.OK);
    }

    @PostMapping("/createCourse")
    public ResponseEntity<ResponseDto> createCourse(@RequestBody CourseDb courseDb, HttpServletRequest request){
        return new ResponseEntity<>(adminService.addCourse(courseDb,request), HttpStatus.CREATED);
    }

    @PostMapping("/addModule/{courseId}")
    public ResponseEntity<ResponseDto> addModuleToCourse(@PathVariable("courseId") Long id, @RequestBody ModulesDb modulesDb){
        return new ResponseEntity<>(adminService.addModulesIntoTheCourse(id,modulesDb),HttpStatus.CREATED);
    }

    @PostMapping("/updateCourse/{courseId}")
    public ResponseEntity<ResponseDto> updateCourse(@PathVariable("courseId") Long id, @RequestBody CourseDb courseDb){
        return new ResponseEntity<>(adminService.updateCourse(id,courseDb),HttpStatus.OK);
    }

    @PostMapping("/updateModule/{moduleId}")
    public ResponseEntity<ResponseDto> updateModule(@PathVariable("moduleId") Long id,@RequestBody ModulesDb modulesDb){
        return new ResponseEntity<>(adminService.UpdateModule(id,modulesDb),HttpStatus.OK);
    }

    @DeleteMapping("/deletedModule/{moduleId}")
    public ResponseEntity<ResponseDto> deleteModule(@PathVariable("moduleId") Long id){
        return new ResponseEntity<>(adminService.dropModule(id),HttpStatus.OK);
    }

    @DeleteMapping("/deletedCourse/{courseId}")
    public ResponseEntity<ResponseDto> deleteCourse(@PathVariable("courseId") Long id){
        return new ResponseEntity<>(adminService.dropCourse(id),HttpStatus.OK);
    }
}
