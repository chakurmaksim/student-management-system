package com.epam.ahnl.service.dto

import com.epam.ahnl.model.entity.Student
import java.time.LocalDate

data class StudentView(
        val id: String,
        val fullName: String,
        val birthDate: LocalDate,
        var faculty: String,
        var address: Student.Address
)