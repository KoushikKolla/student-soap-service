package com.example.student_soap_service.endpoint;

import com.example.student_soap_service.dto.StudentDto;
import com.example.student_soap_service.generated.*;
import com.example.student_soap_service.service.StudentService;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse getAllStudents(@RequestPayload GetAllStudentsRequest request) {
        List<StudentDto> students = studentService.getAllStudents(request.getPageNumber(), request.getPageSize());
        StudentsType studentType = new StudentsType();
        for (StudentDto dto : students) {

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

            studentType.getStudent()
                    .add(student);
        }

        GetAllStudentsResponse response =
                new GetAllStudentsResponse();

        response.setStudents(
                studentType);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentsByBranchAndSemesterRequest")
    @ResponsePayload
    public GetStudentsByBranchAndSemesterResponse getStudentsByBranchAndSemester(@RequestPayload GetStudentsByBranchAndSemesterRequest request) {
        List<StudentDto> students = studentService.getStudentsByBranchAndSemester(request.getBranch(), request.getSemester(), request.getPageNumber(), request.getPageSize());
        StudentsType studentType = new StudentsType();
        for (StudentDto dto : students) {

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

            studentType.getStudent()
                    .add(student);
        }

        GetStudentsByBranchAndSemesterResponse response =
                new GetStudentsByBranchAndSemesterResponse();

        response.setStudents(
                studentType);

        return response;
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "addStudentRequest")
    @ResponsePayload
    public AddStudentResponse addStudent(
            @RequestPayload
            AddStudentRequest request) {
        StudentType student =
                request.getStudent();

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

        StudentDto savedStudent =
                studentService.addStudent(dto);

        AddStudentResponse response =
                new AddStudentResponse();

        StudentType responseStudent =
                new StudentType();

        responseStudent.setStudentCode(
                savedStudent.getStudentCode().trim());

        responseStudent.setName(
                savedStudent.getName());

        responseStudent.setEmail(
                savedStudent.getEmail());

        responseStudent.setBranch(
                savedStudent.getBranch());

        responseStudent.setSemester(
                savedStudent.getSemester());
        response.setStudent(
                responseStudent);
        return response;
    }
    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "updateStudentRequest")
    @ResponsePayload
    public UpdateStudentResponse updateStudent(
            @RequestPayload UpdateStudentRequest request) {

        StudentType student =
                request.getStudent();

        StudentDto dto =
                new StudentDto();

        dto.setStudentCode(
                student.getStudentCode().trim());

        dto.setName(
                student.getName());

        dto.setEmail(
                student.getEmail());

        dto.setBranch(
                student.getBranch());

        dto.setSemester(
                student.getSemester());

        StudentDto updatedStudent =
                studentService.updateStudent(dto);

        StudentType responseStudent =
                new StudentType();

        responseStudent.setStudentCode(
                updatedStudent.getStudentCode());

        responseStudent.setName(
                updatedStudent.getName());

        responseStudent.setEmail(
                updatedStudent.getEmail());

        responseStudent.setBranch(
                updatedStudent.getBranch());

        responseStudent.setSemester(
                updatedStudent.getSemester());

        UpdateStudentResponse response =
                new UpdateStudentResponse();

        response.setStudent(
                responseStudent);

        return response;
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "deleteStudentRequest")
    @ResponsePayload
    public DeleteStudentResponse deleteStudent(
            @RequestPayload
            DeleteStudentRequest request) {

        StudentDto deletedStudent =
                studentService.deleteStudent(
                        request.getStudentCode().trim());

        StudentType student =
                new StudentType();

        student.setStudentCode(
                deletedStudent.getStudentCode());

        student.setName(
                deletedStudent.getName());

        student.setEmail(
                deletedStudent.getEmail());

        student.setBranch(
                deletedStudent.getBranch());

        student.setSemester(
                deletedStudent.getSemester());

        DeleteStudentResponse response =
                new DeleteStudentResponse();

        response.setStudent(student);

        return response;
    }
}