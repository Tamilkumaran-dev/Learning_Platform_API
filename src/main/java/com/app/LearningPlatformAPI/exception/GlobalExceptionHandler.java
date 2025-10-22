package com.app.LearningPlatformAPI.exception;

import com.app.LearningPlatformAPI.exception.exceptions.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ExceptionDto> NotFoundException(NotFound notFound, WebRequest webRequest){
        ExceptionDto exception = new ExceptionDto(
                notFound.getMessage(),
                true,
                LocalDateTime.now(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> RunTimeException(RuntimeException runtimeException, WebRequest webRequest){
        ExceptionDto exception = new ExceptionDto(
                runtimeException.getMessage(),
                true,
                LocalDateTime.now(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> defaultException(Exception runtimeException, WebRequest webRequest){
        ExceptionDto exception = new ExceptionDto(
                runtimeException.getMessage(),
                true,
                LocalDateTime.now(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);

    }
}
