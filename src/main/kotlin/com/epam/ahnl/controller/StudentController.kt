package com.epam.ahnl.controller

import com.epam.ahnl.controller.response.ResponseMessage
import com.epam.ahnl.service.StudentService
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

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
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable("id") studentId: String): StudentView {
        return service.findById(studentId)
    }

    @ApiOperation(value = "Keep a Student Information")
    @ApiImplicitParam(name = "student", value = "Student Detailed Entities student", required = true,
            dataType = "StudentDto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun insertStudent(@RequestBody student: StudentDto): ResponseMessage {
        val message = "studentID: ${service.insert(student).id}"
        return ResponseMessage(message = message)
    }

    @ApiOperation(value = "Update a Student Information")
    @ApiImplicitParam(name = "student", value = "Student Detailed Entities student with studentId number",
            required = true, dataType = "StudentDto")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateStudent(@PathVariable("id") studentId: String, @RequestBody student: StudentDto) {
        service.update(studentId, student)
    }

    @ApiOperation(value = "Delete student information")
    @ApiImplicitParam(name = "studentId", value = "Student number", required = true, dataType = "String")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable("id") studentId: String) {
        service.deleteById(studentId)
    }
}