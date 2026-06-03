package com.example.student_soap_service.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.jooq.DSLContext;

import com.example.studentsoapservice.jooq.tables.records.StudentsRecord;
import static com.example.studentsoapservice.jooq.tables.Students.STUDENTS;


@Repository
public class StudentRepository {
    private final DSLContext dsl;

    public StudentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public StudentsRecord findByStudentCode(
            String studentCode) {
        System.out.println(
                "Searching for : "
                        + studentCode);
        return dsl
                .selectFrom(STUDENTS)
                .where(
                        STUDENTS.STUDENT_CODE
                                .eq(studentCode)
                )
                .fetchOne();
    }
    public List<StudentsRecord> findAll(int pageNumber,int pageSize){
        int offset=(pageNumber-1)*pageSize;
       return dsl.selectFrom(STUDENTS)
                .limit(pageSize)
                .offset(offset)
                .fetch();
    }
}

