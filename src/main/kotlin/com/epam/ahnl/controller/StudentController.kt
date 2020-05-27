package com.epam.ahnl.controller

import com.epam.ahnl.service.StudentService
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Api(value = "Student Information related interface", tags = ["StudentDto", "StudentView"])
@RestController
@RequestMapping("/students")
class StudentController(val service: StudentService) {

    @ApiOperation(value = "Get a Page of students list")
    @ApiImplicitParam(name = "pageable", value = "Pageable Detailed with page number, size and sort order",
            required = false, dataType = "Pageable")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(pageable: Pageable): Page<StudentView> {
        return service.findAll(pageable)
    }

    @ApiOperation(value = "Get a student by id")
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") studentId: String): StudentView {
        return service.findById(studentId)
    }

    @ApiOperation(value = "Keep a Student Information")
    @ApiImplicitParam(name = "student", value = "Student Detailed Entities student", required = true,
            dataType = "StudentDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun insertStudent(@RequestBody student: StudentDto): StudentView {
        return service.insert(student)
    }

    @ApiOperation(value = "Update a Student Information")
    @ApiImplicitParam(name = "student", value = "Student Detailed Entities student with studentId number",
            required = true, dataType = "StudentDto")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateStudent(@RequestBody student: StudentDto): StudentView {
        return service.update(student)
    }

    @ApiOperation(value = "Delete student information")
    @ApiImplicitParam(name = "studentId", value = "Student number", required = true, dataType = "String")
    @DeleteMapping
    fun deleteStudent(@RequestParam("id") studentId: String): ResponseEntity<String> {
        service.deleteById(studentId)
        return ResponseEntity("Student with ID $studentId was deleted successfully", HttpStatus.OK)
    }
}