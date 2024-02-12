package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.Impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void addStudent() {
        when(studentRepository.save(HARRY)).thenReturn(HARRY);
        assertEquals(HARRY, studentService.addStudent(HARRY));
    }

    @Test
    public void getStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(HARRY));
        assertEquals(studentService.getStudent(1L), HARRY);
    }

    @Test
    public void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(HARRY, HERMIONE));
        assertEquals(studentService.getAllStudents(), List.of(HARRY, HERMIONE));
    }

    @Test
    public void editStudent() {
        when(studentRepository.save(HARRY)).thenReturn(HARRY);
        assertEquals(HARRY, studentService.addStudent(HARRY));
    }

    @Test
    public void deleteStudent() {
        // TODO
    }

    @Test
    public void getStudentsByColor() {
        when(studentRepository.findByAge(11)).thenReturn(List.of(HARRY));
        assertEquals(studentService.getStudentsByAge(11), List.of(HARRY));
    }

    @Test
    public void getStudentsByAgeBetween() {
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(HARRY));
        assertEquals(studentService.getStudentsByAgeBetween(anyInt(), anyInt()), List.of(HARRY));
    }

    @Test
    public void getFaculty() {
        // Todo
    }
}