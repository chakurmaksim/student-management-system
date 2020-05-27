package com.epam.ahnl.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate

@Document(value = "students")
data class Student(
        @Id val id: String? = null,
        @Field(name = "name") val firstName: String,
        @Field(name = "surname") val lastName: String,
        @Field(name = "birth_date") val birthDate: LocalDate,
        @Field(name = "faculty") var faculty: String,
        @Field(name = "address") var address: Address) {
    var dateOfCreation: LocalDate? = null
        set(value) {
            if (id == null) {
                field = value
            }
        }
    var dateOfModification: LocalDate? = null
        set(value) {
            if (dateOfCreation != null) {
                field = value
            }
        }

    data class Address(val country: String, val city: String, val street: String)
}