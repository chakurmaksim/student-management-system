package com.epam.ahnl.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import java.util.Optional

@NoRepositoryBean
interface BaseCrudRepository<T, ID> : Repository<T, ID> {
    fun findAll(pageable: Pageable): Page<T>
    fun findById(id: ID): Optional<T>
    fun insert(obj: T): T
    fun save(obj: T): Unit
    fun deleteById(id: ID): Unit
}