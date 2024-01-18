package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {

    private final FacultyService facultyService = new FacultyService();
    private Faculty faculty;
    private Faculty facultyUpdate;

    @BeforeEach
    void setUp() {
        faculty = new Faculty(1L, "Faculty 1", "Orange");
        facultyUpdate = new Faculty(1L, "Faculty 1 update", "Black");
        facultyService.addFaculty(faculty);
    }

    @Test
    void addFaculty() {
        assertEquals(faculty, facultyService.addFaculty(faculty));
    }

    @Test
    void getFaculty() {
        assertEquals(faculty, facultyService.getFaculty(1L));
    }

    @Test
    void getAllFaculties() {
       assertEquals(List.of(faculty), facultyService.getAllFaculties());
    }

    @Test
    void editFaculty() {
        assertEquals(facultyUpdate, facultyService.editFaculty(facultyUpdate));
    }

    @Test
    void deleteFaculty() {
        assertEquals(faculty, facultyService.deleteFaculty(1L));
    }

    @Test
    void getFacultiesByColor() {
        assertEquals(List.of(faculty), facultyService.getFacultiesByColor("Orange"));
    }
}