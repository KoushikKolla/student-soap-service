package com.example.student_soap_service.exception;

public class DuplicateStudentException extends RuntimeException{
    public DuplicateStudentException(
            String studentCode) {
        super("Student already exists : " + studentCode);
    }
}
