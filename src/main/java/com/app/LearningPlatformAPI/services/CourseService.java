package com.app.LearningPlatformAPI.services;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.ModulesDb;
import com.app.LearningPlatformAPI.repository.CourseRepository;
import com.app.LearningPlatformAPI.repository.ModuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepo;
    private ModuleRepository moduleRepo;

    public ResponseDto getCourse(Long id){
        Optional<CourseDb> course = courseRepo.findById(id);

        return new ResponseDto("Get course","Course",course);
    }

    public ResponseDto getModule(Long id) {

        Optional<ModulesDb> modulesDb = moduleRepo.findById(id);

        return new ResponseDto("Get specific module","module",Optional.of(modulesDb.get()));
    }
}
