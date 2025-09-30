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
            IllegalArgumentException ex, WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TaskListNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleTaskListNotFoundException
            (TaskListNotFoundException taskListNotFoundException,WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                taskListNotFoundException.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TaskNotFoundException.class})
    public ResponseEntity<?> handleTaskNotFoundException
            (TaskNotFoundException taskNotFoundException, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                taskNotFoundException.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TaskLIstAlreadyHasIdException.class})
    public ResponseEntity<?> handleTaskListAlreadyHasIdException
            (TaskLIstAlreadyHasIdException taskLIstAlreadyHasId, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                taskLIstAlreadyHasId.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TaskAlreadyHasIdException.class})
    public ResponseEntity<?> handleTaskAlreadyHasIdException
            (TaskAlreadyHasIdException taskAlreadyHasId, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                taskAlreadyHasId.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
