package com.epam.ahnl.service.mapper

import com.epam.ahnl.repository.entity.StudentModel
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import kotlin.reflect.full.memberProperties

fun StudentModel.toStudentView() = StudentView(
        id ?: "empty",
        "$firstName $lastName",
        birthDate,
        faculty,
        address
)

fun StudentDto.toStudent() = with(::StudentModel) {
    val propertiesByName = StudentDto::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter -> propertiesByName[parameter.name]?.get(this@toStudent) })
}