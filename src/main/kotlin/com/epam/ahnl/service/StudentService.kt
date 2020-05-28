package com.epam.ahnl.service

import com.epam.ahnl.repository.entity.StudentModel
import com.epam.ahnl.repository.StudentRepository
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import com.epam.ahnl.service.exception.StudentNotFoundException
import com.epam.ahnl.service.mapper.toStudent
import com.epam.ahnl.service.mapper.toStudentView
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.streams.toList

@Service
class StudentService(val repository: StudentRepository) {

    fun findAll(pageable: org.springframework.data.domain.Pageable): Page<StudentView> {
        val students: Page<StudentModel> = repository.findAll(pageable)
        val studentViews: List<StudentView?> = students.get()
                .map { student: StudentModel? -> student?.toStudentView() }.toList()
        return PageImpl(studentViews, students.pageable, students.totalElements)
    }

    fun findById(id: String): StudentView {
        val student = repository.findById(id)
                .orElseThrow { StudentNotFoundException("The student with ID $id does not exists") }
        return student.toStudentView()
    }

    fun insert(studentDto: StudentDto): StudentView {
        val student = studentDto.toStudent()
        student.dateOfCreation = LocalDate.now()
        val createdStudent = repository.insert(student)
        return createdStudent.toStudentView()
    }

    fun update(studentId: String, studentDto: StudentDto) {
        if (repository.findById(studentId).isEmpty) {
            throw StudentNotFoundException("The student with ID $studentId does not exists")
        }
        val student = studentDto.toStudent()
        student.id = studentId
        student.dateOfModification = LocalDate.now()
        repository.save(student)
    }

    fun deleteById(id: String) {
        repository.deleteById(id)
    }
}