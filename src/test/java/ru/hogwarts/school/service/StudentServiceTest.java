package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private final StudentService studentService = new StudentService();
    private Student student;
    private Student studentUpdate;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Harry Potter", 11);
        studentUpdate = new Student(1L, "Hermione Granger", 12);
        studentService.addStudent(student);
    }

    @Test
    void addStudent() {
        assertEquals(student, studentService.addStudent(student));
    }

    @Test
    void getStudent() {
        assertEquals(student, studentService.getStudent(1L));
    }

    @Test
    void getAllStudents() {
        assertEquals(List.of(student), studentService.getAllStudents());
    }

    @Test
    void editStudent() {
        assertEquals(studentUpdate, studentService.editStudent(studentUpdate));
    }

    @Test
    void deleteStudent() {
        assertEquals(student, studentService.deleteStudent(1L));
    }

    @Test
    void getStudentsByAge() {
        assertEquals(List.of(student), studentService.getStudentsByAge(11));
    }
}