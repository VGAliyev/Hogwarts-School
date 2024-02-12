package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.Impl.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.constants.TestConstants.FACULTY;
import static ru.hogwarts.school.constants.TestConstants.GRYFFINDOR;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void addFacultyTest() throws Exception {
        Long facultyId = 1L;
        String facultyName = "Faculty";
        String facultyColor = "Black";

        Faculty faculty = new Faculty(facultyId, facultyName, facultyColor);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", facultyId);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", facultyColor);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculties/")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void getFacultyTest() throws Exception {
        when(facultyRepository.findById(any())).thenReturn(Optional.of(GRYFFINDOR));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void getAllFacultiesTest() throws Exception {
        when(facultyRepository.findAll()).thenReturn(List.of(GRYFFINDOR));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(1))
                .andExpect(jsonPath("$.[*].name").value("Gryffindor"))
                .andExpect(jsonPath("$.[*].color").value("Red"));
    }

    @Test
    public void getFacultiesByColorTest() throws Exception {
        when(facultyRepository.findByColor(any(String.class))).thenReturn(List.of(GRYFFINDOR));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/color/{color}", "Red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(1))
                .andExpect(jsonPath("$.[*].name").value("Gryffindor"))
                .andExpect(jsonPath("$.[*].color").value("Red"));
    }

    @Test
    public void getFacultiesByNameOrColorTest() throws Exception {
        when(facultyRepository.findByNameOrColorIgnoreCase(any(String.class), any(String.class))).thenReturn(List.of(GRYFFINDOR));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/findByNameOrColor?name=Gryffindor&color=Red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(1))
                .andExpect(jsonPath("$.[*].name").value("Gryffindor"))
                .andExpect(jsonPath("$.[*].color").value("Red"));
    }

    @Test
    public void getStudentsTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/{id}/students", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value("Harry Potter"));
    }

    @Test
    public void editFacultyTest() throws Exception {
        Long facultyId = 1L;
        String facultyName = "Not Faculty";
        String facultyColor = "Yellow";

        Faculty faculty = new Faculty(facultyId, facultyName, facultyColor);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", facultyId);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", facultyColor);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/faculties/")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Not Faculty"));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        when(facultyRepository.findById(any())).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculties/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
