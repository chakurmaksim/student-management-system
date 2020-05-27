package com.epam.ahnl.service.mapper

import com.epam.ahnl.model.entity.Student
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import kotlin.reflect.full.memberProperties

fun Student.toStudentView() = StudentView(
        id ?: "empty",
        "$firstName $lastName",
        birthDate,
        faculty,
        address
)

fun StudentDto.toStudent() = with(::Student) {
    val propertiesByName = StudentDto::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter -> propertiesByName[parameter.name]?.get(this@toStudent) })
}