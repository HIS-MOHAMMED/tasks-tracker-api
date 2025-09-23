package com.hisham.tasks.exceptions;

public class TaskAlreadyHasIdException extends RuntimeException{
    public TaskAlreadyHasIdException(String message){
        super(message);
    }
}
