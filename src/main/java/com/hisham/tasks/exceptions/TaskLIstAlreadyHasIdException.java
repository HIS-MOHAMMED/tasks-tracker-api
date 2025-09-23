package com.hisham.tasks.exceptions;

public class TaskLIstAlreadyHasIdException extends RuntimeException{
    public TaskLIstAlreadyHasIdException(String message){
        super(message);
    }
}
