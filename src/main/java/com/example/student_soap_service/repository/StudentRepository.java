package com.example.student_soap_service.repository;

import com.example.student_soap_service.dto.StudentDto;
import com.example.student_soap_service.dto.StudentSearchCriteria;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;


import com.example.studentsoapservice.jooq.tables.records.StudentsRecord;

import java.util.List;

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
    public List<StudentsRecord> findByBranchAndSemester(String branch,int semester,int pageNumber,int pageSize){
        int offset=(pageNumber-1)*pageSize;
        return dsl.selectFrom(STUDENTS)
                .where(STUDENTS.BRANCH.eq(branch))
                .and(STUDENTS.SEMESTER.eq(semester))
                .limit(pageSize)
                .offset(offset)
                .fetch();
    }
    public boolean existsByStudentCode(
            String studentCode) {

        return dsl.fetchExists(

                dsl.selectFrom(STUDENTS)

                        .where(
                                STUDENTS.STUDENT_CODE
                                        .eq(studentCode)
                        )
        );
    }

    public StudentsRecord save(StudentDto dto){

        return dsl.insertInto(STUDENTS)
                .set(STUDENTS.STUDENT_CODE,dto.getStudentCode().trim().toUpperCase())
                .set(STUDENTS.NAME,dto.getName().trim())
                .set(STUDENTS.EMAIL, dto.getEmail().trim())
                .set(STUDENTS.BRANCH, dto.getBranch().trim())
                .set(STUDENTS.SEMESTER,dto.getSemester())
                .returning()
                .fetchOne();
    }

    public StudentsRecord updateStudent(
            StudentDto dto) {

        dsl.update(STUDENTS)

                .set(
                        STUDENTS.NAME,
                        dto.getName().trim())

                .set(
                        STUDENTS.EMAIL,
                        dto.getEmail().trim())

                .set(
                        STUDENTS.BRANCH,
                        dto.getBranch().trim())

                .set(
                        STUDENTS.SEMESTER,
                        dto.getSemester())

                .where(
                        STUDENTS.STUDENT_CODE
                                .eq(dto.getStudentCode().trim())
                )

                .execute();
//
//        int rows = dsl.update(STUDENTS)
//                .set(STUDENTS.NAME, dto.getName().trim())
//                .set(STUDENTS.EMAIL, dto.getEmail().trim())
//                .set(STUDENTS.BRANCH, dto.getBranch().trim())
//                .set(STUDENTS.SEMESTER, dto.getSemester())
//                .where(STUDENTS.STUDENT_CODE.eq(dto.getStudentCode().trim()))
//                .execute();
//
//        System.out.println("Rows Updated = " + rows);

        return findByStudentCode(
                dto.getStudentCode());
    }

    public void deleteStudent(String studentCode){
        dsl.deleteFrom(STUDENTS)
                .where(STUDENTS.STUDENT_CODE.eq(studentCode))
                .execute();
    }
    public List<StudentsRecord> searchStudents(
            StudentSearchCriteria criteria,
            int pageNumber,
            int pageSize) {

        int offset =
                (pageNumber - 1) * pageSize;

        Condition condition =
                DSL.trueCondition();


        if(criteria.getStudentCode() != null
                && !criteria.getStudentCode().isBlank()) {

            condition =
                    condition.and(

                            STUDENTS.STUDENT_CODE.eq(
                                    criteria.getStudentCode().trim()
                            )
                    );
        }

        if(criteria.getName() != null
                && !criteria.getName().isBlank()) {

            condition =
                    condition.and(

                            STUDENTS.NAME.likeIgnoreCase(

                                    "%"
                                            + criteria.getName().trim()
                                            + "%"
                            )
                    );
        }
        if(criteria.getEmail() != null
                && !criteria.getEmail().isBlank()) {

            condition =
                    condition.and(

                            STUDENTS.EMAIL.likeIgnoreCase(

                                    "%"
                                            + criteria.getEmail().trim()
                                            + "%"
                            )
                    );
        }

        if(criteria.getBranch() != null
                && !criteria.getBranch().isBlank()) {

            condition =
                    condition.and(

                            STUDENTS.BRANCH.eq(
                                    criteria.getBranch()
                                            .trim()
                                            .toUpperCase()
                            )
                    );
        }

        if(criteria.getSemester() != null) {

            condition =
                    condition.and(

                            STUDENTS.SEMESTER.eq(
                                    criteria.getSemester()
                            )
                    );
        }

        return dsl.selectFrom(STUDENTS)

                .where(condition)

                .limit(pageSize)

                .offset(offset)

                .fetch();
    }


}

