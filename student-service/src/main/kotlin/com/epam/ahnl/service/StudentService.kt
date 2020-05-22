package com.epam.ahnl.service

import com.epam.ahnl.model.entity.Student
import com.epam.ahnl.model.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(private val repository: StudentRepository) {

    fun insert(student: Student): Student {
        return repository.insert(student)
    }

    fun deleteById(id: String) {
        repository.deleteById(id)
    }
}