package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HomeService {

    private CourseRepository courseRepo;

    public ResponseDto getAllCourse(){
        List<CourseDb> courseList =  courseRepo.findAll();
        return new ResponseDto("Get All the course","");
    }


    public Page<CourseDb> searchCourse(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        if (keyword.equals("allCourse")){
            return courseRepo.findAll(pageable);
        }
        else{
            return courseRepo.searchCourse(keyword,pageable);
        }
    }




}
