package com.example.student_soap_service.endpoint;

import com.example.student_soap_service.dto.StudentDto;
import com.example.student_soap_service.service.StudentService;
import com.example.student_soap_service.generated.GetStudentRequest;
import com.example.student_soap_service.generated.GetStudentResponse;
import com.example.student_soap_service.generated.StudentType;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StudentEndpoint {

    private static final String NAMESPACE_URI =
            "http://example.com/student";

    private final StudentService studentService;

    public StudentEndpoint(
            StudentService studentService) {

        this.studentService = studentService;
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "getStudentRequest")
    @ResponsePayload
    public GetStudentResponse getStudent(
            @RequestPayload
            GetStudentRequest request) {

        StudentDto dto =
                studentService.getStudent(
                        request.getStudentCode());

        StudentType student =
                new StudentType();

        student.setStudentCode(
                dto.getStudentCode());

        student.setName(
                dto.getName());

        student.setEmail(
                dto.getEmail());

        student.setBranch(
                dto.getBranch());

        student.setSemester(
                dto.getSemester());

        GetStudentResponse response =
                new GetStudentResponse();

        response.setStudent(student);

        return response;
    }
}