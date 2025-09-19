package com.hisham.tasks.exceptions;

public class TaskListNotFoundException extends RuntimeException{
    public TaskListNotFoundException(String message){
        super(message);
    }
}
