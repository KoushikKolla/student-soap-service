package com.example.student_soap_service.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String studentCode){
        super("Student Not Found"+ studentCode);
    }
}
