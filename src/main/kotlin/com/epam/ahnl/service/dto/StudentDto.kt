package com.epam.ahnl.service.dto

import com.epam.ahnl.repository.entity.Address
import java.time.LocalDate

data class StudentDto (
        val id: String? = null,
        val firstName: String,
        val lastName: String,
        val birthDate: LocalDate,
        var faculty: String,
        var address: Address
) {
    init {
        require(firstName.length in 3..30) {
            "Name should contain at least 3 and no more than 30 characters"
        }
        require(lastName.length in 3..30) {
            "Last name should contain at least 3 and no more than 30 characters"
        }
        require(birthDate <= LocalDate.now().minusYears(16)) {
            "Student birth date can not be less than 16 years before the current date"
        }
        require(faculty.isNotBlank()) {
            "Faculty name is blank"
        }
    }
}