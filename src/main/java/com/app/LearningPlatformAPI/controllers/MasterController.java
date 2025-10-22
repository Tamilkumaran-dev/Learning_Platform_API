package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.MasterDb;
import com.app.LearningPlatformAPI.services.MasterUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@AllArgsConstructor
public class MasterController {

    private MasterUserService masterService;

    @PostMapping("/auth/master/signUp")
    public ResponseEntity<ResponseDto> addMasterUser(@RequestBody MasterDb master){
        return new ResponseEntity<>(masterService.signUpMaster(master), HttpStatus.CREATED);
    }

    @GetMapping("/master/profile")
    public ResponseEntity<ResponseDto> getMasterProfile(HttpServletRequest request){
        return new ResponseEntity<>(masterService.getMasterProfile(request),HttpStatus.OK);
    }

    @GetMapping("/master/getAdminUser/{keyword}/{page}/{size}")
    public ResponseEntity<Page<AdminDb>> getUnVerified(@PathVariable("keyword") String keyword,@PathVariable("page") int page, @PathVariable("size") int size){
        return new ResponseEntity<>(masterService.getUnVerifiedAdmin(keyword,page,size),HttpStatus.OK);
    }

    @DeleteMapping("/master/deleteAdmin/{adminId}")
    public ResponseEntity<ResponseDto> deleteAdmin(@PathVariable("adminId") Long id){
        return new ResponseEntity<>(masterService.deleteAdmin(id),HttpStatus.OK);
    }



}
