package ru.hogwarts.school.repository;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface Last5Students {
    List<Student> getLast5Students();
}
