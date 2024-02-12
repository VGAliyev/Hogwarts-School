package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudent(long id);

    Collection<Student> getAllStudents();

    Student editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> getStudentsByAge(int age);

    Collection<Student> getStudentsByAgeBetween(int ageMin, int ageMax);

    Faculty getFaculty(Long id);

    Integer getStudentsCount();

    Float getAverageAge();

    List<Student> getLast5Students();
}
