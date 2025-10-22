package com.app.LearningPlatformAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String message;
    private String resKeyword;
    private Boolean resStatus = true;
    private Boolean isException = false;
    private Optional<?> data;

    public ResponseDto(String message, String resKeyword){
        this.message = message;
        this.resKeyword = resKeyword;
    }

    public ResponseDto(String message, String resKeyword, Optional<?> data){
        this.message = message;
        this.resKeyword = resKeyword;
        this.data = data;
    }
}
