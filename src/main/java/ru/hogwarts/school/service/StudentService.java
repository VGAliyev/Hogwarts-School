package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudent(long id);

    Collection<Student> getAllStudents();

    Student editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> getStudentsByAge(int age);
}
