package com.epam.ahnl.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(value = "students")
data class Student(@Id val id: String? = null, @Field(name = "name") val name: String) {

}