package com.example.student_soap_service.service;

import com.example.student_soap_service.dto.StudentDto;
import com.example.student_soap_service.dto.StudentSearchCriteria;
import com.example.student_soap_service.exception.DuplicateStudentException;
import com.example.student_soap_service.exception.StudentNotFoundException;
import com.example.student_soap_service.repository.StudentRepository;
import com.example.studentsoapservice.jooq.tables.records.StudentsRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<StudentDto> getAllStudents
            (int pageNumber,int pageSize){
        List<StudentsRecord> students=
                repository.findAll(pageNumber,pageSize);
        List<StudentDto> results=new ArrayList<>();
        for(StudentsRecord student : students){
            results.add(mapToDto(student));
        }
        return results;
    }
    public List<StudentDto>
    getStudentsByBranchAndSemester(
            String branch,
            int semester,
            int pageNumber,
            int pageSize){
        List<StudentsRecord> students
                = repository.findByBranchAndSemester(branch.trim().toUpperCase(), semester, pageNumber, pageSize);
        System.out.println("Branch = {" + branch+"}");
        System.out.println("Semester = " + semester);
        System.out.println("Students Found = "
                + students.size());
        List<StudentDto> results= new ArrayList<>();
        for(StudentsRecord student : students){
            results.add(mapToDto(student));
        }
        return results;

    }
    public StudentDto addStudent(StudentDto dto){
        System.out.println(
                "Student Code = {" +
                        dto.getStudentCode().trim() +
                        "}");
        if(repository.existsByStudentCode(
                dto.getStudentCode().trim())) {

            throw new DuplicateStudentException(
                    dto.getStudentCode());
        }
        StudentsRecord savedStudent= repository.save(dto);
        return mapToDto(savedStudent);
    }

    public StudentDto updateStudent(
            StudentDto dto) {

        StudentsRecord existingStudent =
                repository.findByStudentCode(
                        dto.getStudentCode());

        if(existingStudent == null) {

            throw new StudentNotFoundException(
                    dto.getStudentCode());
        }

        StudentsRecord updatedStudent =
                repository.updateStudent(dto);

        return mapToDto(
                updatedStudent);
    }

    public StudentDto deleteStudent(String studentCode){
        StudentsRecord student= repository.findByStudentCode(studentCode);
        if(student==null){
            throw new StudentNotFoundException(studentCode);

        }
        StudentDto deletedStudent= mapToDto(student);
        repository.deleteStudent(studentCode);
        return deletedStudent;
    }
    public List<StudentDto> searchStudents(
            StudentSearchCriteria criteria,
            int pageNumber,
            int pageSize){
        List<StudentsRecord> students=
                repository.searchStudents(criteria,pageNumber,pageSize);
        ArrayList<StudentDto> results= new ArrayList<>();
        for(StudentsRecord student : students){
            results.add(mapToDto(student));
        }
        return results;
    }
}
