package com.hisham.tasks.exceptions;

import com.hisham.tasks.domain.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleExceptions(
            RuntimeException ex, WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TaskListNotFoundException.class})
    public ResponseEntity<?> handleTaskListNotFoundException(TaskListNotFoundException taskListNotFoundException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),taskListNotFoundException.getMessage(),"none");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TaskNotFoundException.class})
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException taskNotFoundException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),taskNotFoundException.getMessage(),"none");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TaskLIstAlreadyHasIdException.class})
    public ResponseEntity<?> handleTaskListAlreadyHasIdException(TaskLIstAlreadyHasIdException taskLIstAlreadyHasId){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), taskLIstAlreadyHasId.getMessage(), "noe");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TaskAlreadyHasIdException.class})
    public ResponseEntity<?> handleTaskAlreadyHasIdException(TaskAlreadyHasIdException taskAlreadyHasId){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), taskAlreadyHasId.getMessage(), "noe");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
