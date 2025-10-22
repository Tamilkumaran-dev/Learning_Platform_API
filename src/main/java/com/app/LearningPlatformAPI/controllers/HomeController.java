package com.app.LearningPlatformAPI.controllers;


import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.services.HomeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@AllArgsConstructor
public class HomeController {

    private HomeService homeService;


    @GetMapping("/home")
    public ResponseEntity<String> getHome(){
        return new ResponseEntity<>("Home Page", HttpStatus.OK);
    }

    @GetMapping("/home/search/{keyword}/{page}/{size}")
    public ResponseEntity<Page<CourseDb>> searchCourse(@PathVariable("keyword") String keyword, @PathVariable("page") int page, @PathVariable("size") int size){
        return new ResponseEntity<>(homeService.searchCourse(keyword,page,size),HttpStatus.OK);
    }


}
