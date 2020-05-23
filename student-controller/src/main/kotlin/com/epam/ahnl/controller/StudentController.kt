package com.epam.ahnl.controller

import com.epam.ahnl.service.StudentService
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController(val service: StudentService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(pageable: Pageable): Page<StudentView> {
        return service.findAll(pageable)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") studentId: String): StudentView {
        return service.findById(studentId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun insertStudent(@RequestBody student: StudentDto): StudentView {
        return service.insert(student)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateStudent(@RequestBody student: StudentDto): StudentView {
        return service.update(student)
    }

    @DeleteMapping
    fun deleteStudent(@RequestParam("id") studentId: String): ResponseEntity<String> {
        service.deleteById(studentId)
        return ResponseEntity("Student with ID $studentId was deleted successfully", HttpStatus.OK)
    }
}