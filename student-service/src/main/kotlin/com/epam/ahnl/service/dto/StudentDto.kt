package com.epam.ahnl.service.dto

import com.epam.ahnl.model.entity.Student
import java.time.LocalDate

data class StudentDto(
        val id: String? = null,
        val firstName: String,
        val lastName: String,
        val birthDate: LocalDate,
        var faculty: String,
        var address: Student.Address
)