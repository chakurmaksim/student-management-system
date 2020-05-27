package com.epam.ahnl.model.repository

import com.epam.ahnl.model.entity.Student
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository: BaseCrudRepository<Student, String> {
}