package com.epam.ahnl.service.dto

import com.epam.ahnl.repository.entity.Address
import java.time.LocalDate

data class StudentView(
        val id: String,
        val fullName: String,
        val birthDate: LocalDate,
        var faculty: String,
        var address: Address
)