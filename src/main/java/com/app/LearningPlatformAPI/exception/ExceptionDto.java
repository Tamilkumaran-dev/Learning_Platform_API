package com.app.LearningPlatformAPI.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private String message;
    private Boolean isException = true;
    private LocalDateTime timeStamp;
    private String path;


}
