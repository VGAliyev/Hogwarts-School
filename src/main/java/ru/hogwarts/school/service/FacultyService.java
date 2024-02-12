package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFaculty(long id);

    Collection<Faculty> getAllFaculties();

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> getFacultiesByColor(String color);

    Collection<Faculty> getFacultiesByNameOrColor(String name, String color);

    Collection<Student> getStudents(Long id);
}
