package com.epam.ahnl.repository

import com.epam.ahnl.repository.entity.StudentModel
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository: BaseCrudRepository<StudentModel, String> {
}