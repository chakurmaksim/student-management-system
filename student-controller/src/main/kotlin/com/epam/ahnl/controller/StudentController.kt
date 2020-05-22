package com.epam.ahnl.controller

import com.epam.ahnl.model.entity.Student
import com.epam.ahnl.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController(val service: StudentService) {

    @PostMapping
    fun insertStudent(@RequestBody student: Student): Student {
        return service.insert(student)
    }

    @DeleteMapping
    fun deleteStudent(@RequestParam("id") studentId: String): ResponseEntity<String> {
        service.deleteById(studentId)
        return ResponseEntity("Deleted successfully", HttpStatus.OK)
    }

}