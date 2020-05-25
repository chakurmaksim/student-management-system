package com.epam.ahnl.service

import com.epam.ahnl.model.entity.Student
import com.epam.ahnl.model.repository.StudentRepository
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import com.epam.ahnl.service.exception.StudentNotFoundException
import com.epam.ahnl.service.mapper.toStudent
import com.epam.ahnl.service.mapper.toStudentView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.streams.toList

@Service
class StudentService {
    @Autowired
    lateinit var repository: StudentRepository

    fun findAll(pageable: org.springframework.data.domain.Pageable): Page<StudentView> {
        val students: Page<Student> = repository.findAll(pageable)
        val studentViews: List<StudentView?> = students.get()
                .map { student: Student? -> student?.toStudentView() }.toList()
        return PageImpl(studentViews, students.pageable, students.totalElements)
    }

    fun findById(id: String): StudentView {
        val student = repository.findById(id)
                .orElseThrow { StudentNotFoundException("The student with ID $id does not exists") }
        return student.toStudentView()
    }

    fun insert(studentDto: StudentDto): StudentView {
        val student = studentDto.toStudent()
        println(student)
        student.dateOfCreation = LocalDate.now()
        val createdStudent = repository.insert(student)
        return createdStudent.toStudentView()
    }

    fun update(studentDto: StudentDto): StudentView {
        val student = studentDto.toStudent()
        val id: String = student.id ?: throw StudentNotFoundException("The student with empty ID does not exists")
        if (repository.findById(id).isEmpty) {
            throw StudentNotFoundException("The student with ID $id does not exists")
        }
        student.dateOfModification = LocalDate.now()
        repository.save(student)
        return student.toStudentView()
    }

    fun deleteById(id: String) {
        repository.deleteById(id)
    }
}