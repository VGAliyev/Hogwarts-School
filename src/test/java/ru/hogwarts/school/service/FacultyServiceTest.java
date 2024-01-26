package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.Impl.FacultyServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    public void addFaculty() {
        when(facultyRepository.save(GRYFFINDOR)).thenReturn(GRYFFINDOR);
        assertEquals(GRYFFINDOR, facultyService.addFaculty(GRYFFINDOR));
    }

    @Test
    public void getFaculty() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(GRYFFINDOR));
        assertEquals(facultyService.getFaculty(1L), GRYFFINDOR);
    }

    @Test
    public void getAllFaculties() {
        when(facultyRepository.findAll()).thenReturn(List.of(GRYFFINDOR, SLYTHERIN));
        assertEquals(facultyService.getAllFaculties(), List.of(GRYFFINDOR, SLYTHERIN));
    }

    @Test
    public void editFaculty() {
        when(facultyRepository.save(GRYFFINDOR)).thenReturn(GRYFFINDOR);
        assertEquals(GRYFFINDOR, facultyService.addFaculty(GRYFFINDOR));
    }

    @Test
    public void deleteFaculty() {
        // TODO
    }

    @Test
    public void getFacultiesByColor() {
        when(facultyRepository.findByColor("Green")).thenReturn(List.of(SLYTHERIN));
        assertEquals(facultyService.getFacultiesByColor("Green"), List.of(SLYTHERIN));
    }

    @Test
    public void getFacultyByNameOrColor() {
        when(facultyRepository.findByNameOrColorIgnoreCase(anyString(), anyString())).thenReturn(List.of(GRYFFINDOR));
        assertEquals(facultyService.getFacultiesByNameOrColor(anyString(), anyString()), List.of(GRYFFINDOR));
    }

    //@Test
    //public void getStudents() {
        //Collection<Student> students = spy(new ArrayList<>());
        //students.add(HARRY);
        //doReturn(Optional.of(students)).when(facultyRepository).findById(anyLong());
        //assertEquals(HARRY, facultyService.getStudents(1L));
        // ToDo
    //}
}