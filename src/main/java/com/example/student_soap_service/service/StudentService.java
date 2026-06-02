package com.example.student_soap_service.service;

import com.example.student_soap_service.dto.StudentDto;
import com.example.student_soap_service.exception.StudentNotFoundException;
import com.example.student_soap_service.repository.StudentRepository;
import com.example.studentsoapservice.jooq.tables.records.StudentsRecord;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    private StudentDto mapToDto(
            StudentsRecord student) {

        StudentDto dto =
                new StudentDto();

        dto.setStudentCode(
                student.getStudentCode());

        dto.setName(
                student.getName());

        dto.setEmail(
                student.getEmail());

        dto.setBranch(
                student.getBranch());

        dto.setSemester(
                student.getSemester());

        return dto;
    }
    public StudentDto getStudent(
            String studentCode) {

        System.out.println(
                "Received Student Code : "
                        + studentCode);

        StudentsRecord student =
                repository.findByStudentCode(
                        studentCode.trim());

        System.out.println(
                "Student Record : "
                        + student);

        if(student == null) {

            throw new StudentNotFoundException(
                    studentCode);
        }

        return mapToDto(student);
    }
}
