package com.epam.ahnl.service

import com.epam.ahnl.model.entity.Student
import com.epam.ahnl.model.repository.StudentRepository
import com.epam.ahnl.service.dto.StudentDto
import com.epam.ahnl.service.dto.StudentView
import com.epam.ahnl.service.exception.StudentNotFoundException
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.assertThrows
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
    @MockK
    lateinit var repository: StudentRepository

    @InjectMockKs
    var service: StudentService = StudentService()
    private lateinit var student: Student
    private lateinit var studentView: StudentView

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this)
        student = Student("ID", "First name", "Last name",
                LocalDate.of(2000, 1, 1), "ah",
                Student.Address("Country", "City", "Street"))
        studentView = StudentView("ID", "${student.firstName} ${student.lastName}",
                student.birthDate, student.faculty, student.address)
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun findAll() {
        val pageable: Pageable = PageRequest.of(0, 1)
        val studentList = mutableListOf(student)
        val studentPage: Page<Student> = PageImpl(studentList, pageable, 1)
        every { repository.findAll(pageable) } returns studentPage
        val studentViewList = mutableListOf(studentView)
        val expectedPage: Page<StudentView> = PageImpl(studentViewList, pageable, 1)

        assertEquals(expectedPage, service.findAll(pageable))
        verify { repository.findAll(pageable) }
        confirmVerified(repository)
    }

    @Test
    fun findById() {
        every { repository.findById("ID") } returns Optional.of(student)

        assertEquals(studentView, service.findById("ID"))
        verify { repository.findById("ID") }
        confirmVerified(repository)
    }

    @Test
    fun shouldThrowExceptionWhenResultFindByIdIsEmpty() {
        every { repository.findById("") } returns Optional.empty()
        val assertThrows: StudentNotFoundException = assertThrows { service.findById("") }

        verify { repository.findById("") }
        confirmVerified(repository)
    }

    @Test
    fun insert() {
        val newStudent = Student(null, student.firstName, student.lastName,
                student.birthDate, student.faculty, student.address)
        every { repository.insert(newStudent) } returns student
        val studentDto = StudentDto(newStudent.id, newStudent.firstName, newStudent.lastName,
                newStudent.birthDate, newStudent.faculty, newStudent.address)

        assertEquals(studentView, service.insert(studentDto))
        verify { repository.insert(newStudent) }
        confirmVerified(repository)
    }

    @Test
    fun update() {
        every { repository.findById(student.id ?: "ID") } returns Optional.of(student)
        every { repository.save(student) } answers { Unit }
        val studentDto = StudentDto(student.id, student.firstName, student.lastName,
                student.birthDate, student.faculty, student.address)

        assertEquals(studentView, service.update(studentDto))
        verify {
            repository.findById("ID")
            repository.save(student)
        }
        confirmVerified(repository)
    }

    @Test
    fun shouldThrowExceptionWhenStudentNotUpdated() {
        every { repository.findById("") } returns Optional.empty()
        val assertThrows: StudentNotFoundException = assertThrows { service.update(StudentDto("",
                student.firstName, student.lastName, student.birthDate, student.faculty, student.address)) }

        verify { repository.findById("") }
        confirmVerified(repository)
    }

    @Test
    fun deleteById() {
        every { repository.deleteById(student.id ?: "ID") } answers { Unit }
        service.deleteById("ID")

        verify { repository.deleteById("ID") }
        confirmVerified(repository)
    }
}