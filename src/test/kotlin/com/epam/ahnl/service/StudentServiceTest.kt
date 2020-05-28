package com.epam.ahnl.service

import com.epam.ahnl.repository.entity.Address
import com.epam.ahnl.repository.entity.StudentModel
import com.epam.ahnl.repository.StudentRepository
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import com.epam.ahnl.service.exception.StudentNotFoundException
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.Optional

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StudentServiceTest {
    private val repository: StudentRepository = mockk()
    private val service: StudentService = StudentService(repository)
    private lateinit var student: StudentModel
    private lateinit var expectedStudentView: StudentView
    private val id = "ID"

    @BeforeAll
    fun setUp() {
        student = StudentModel(id, "First name", "Last name",
                LocalDate.of(2000, 1, 1), "ah",
                Address("Country", "City", "Street"))
        expectedStudentView = StudentView(id, "${student.firstName} ${student.lastName}",
                student.birthDate, student.faculty, student.address)
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun findAll() {
        // given
        val pageable: Pageable = PageRequest.of(0, 1)
        val studentList = mutableListOf(student)
        val studentPage: Page<StudentModel> = PageImpl(studentList, pageable, 1)
        val expectedStudentViewList = mutableListOf(expectedStudentView)
        val expectedPage: Page<StudentView> = PageImpl(expectedStudentViewList, pageable, 1)

        every { repository.findAll(pageable) } returns studentPage

        // when
        val actualStudentViewList = service.findAll(pageable)

        // then
        assertEquals(expectedPage, actualStudentViewList)
        verify { repository.findAll(pageable) }
        confirmVerified(repository)
    }

    @Test
    fun findById() {
        // given
        every { repository.findById(id) } returns Optional.of(student)

        // when
        val actualStudentView = service.findById(id)

        // then
        assertEquals(expectedStudentView, actualStudentView)
        verify { repository.findById(id) }
        confirmVerified(repository)
    }

    @Test
    fun shouldThrowExceptionWhenResultFindByIdIsEmpty() {
        // given
        every { repository.findById("") } returns Optional.empty()

        // when
        val assertThrows: StudentNotFoundException = assertThrows { service.findById("") }

        // then
        verify { repository.findById("") }
        confirmVerified(repository)
    }

    @Test
    fun insert() {
        // given
        val newStudent = StudentModel(null, student.firstName, student.lastName,
                student.birthDate, student.faculty, student.address)
        val studentDto = StudentDto(newStudent.id, newStudent.firstName, newStudent.lastName,
                newStudent.birthDate, newStudent.faculty, newStudent.address)

        every { repository.insert(newStudent) } returns student

        // when
        val actualStudentView = service.insert(studentDto)

        // then
        assertEquals(expectedStudentView, actualStudentView)
        verify { repository.insert(newStudent) }
        confirmVerified(repository)
    }

    @Test
    fun update() {
        // given
        val studentDto = StudentDto(student.id, student.firstName, student.lastName,
                student.birthDate, student.faculty, student.address)

        every { repository.findById(id) } returns Optional.of(student)
        every { repository.save(student) } answers { Unit }

        // when
        service.update(id, studentDto)

        // then
        verify {
            repository.findById(id)
            repository.save(student)
        }
        confirmVerified(repository)
    }

    @Test
    fun shouldThrowExceptionWhenStudentNotUpdated() {
        // given
        every { repository.findById("") } returns Optional.empty()

        // when
        val assertThrows: StudentNotFoundException = assertThrows { service.update("", StudentDto("",
                student.firstName, student.lastName, student.birthDate, student.faculty, student.address)) }

        // then
        verify { repository.findById("") }
        confirmVerified(repository)
    }

    @Test
    fun deleteById() {
        // given
        every { repository.deleteById(id) } answers { Unit }

        // when
        service.deleteById(id)

        // then
        verify { repository.deleteById(id) }
        confirmVerified(repository)
    }
}