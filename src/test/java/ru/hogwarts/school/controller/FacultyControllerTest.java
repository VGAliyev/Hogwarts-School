package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    private final Faculty faculty = new Faculty(2L, "Slytherin", "Green");
    private final Faculty gryffindorFaculty = new Faculty(1L, "Gryffindor", "Red");
    private final Student student = new Student(3L, "Draco Malfoy", 17, faculty);

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoadsTest() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void getAllFacultiesTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/", Collection.class).size())
                .isEqualTo(2);
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/2", Faculty.class))
                .isEqualTo(faculty);
    }

    @Test
    public void getFacultiesByColorTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/color/Green", Collection.class).size())
                .isEqualTo(1);
    }

    @Test
    public void findByNameOrColorTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/findByNameOrColor?name=Slytherin", Collection.class).size())
                .isEqualTo(1);
    }

    @Test
    public void getStudentsByFacultyIdTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/2/students", Collection.class).size())
                .isEqualTo(1);
    }

    @Test
    public void addFacultyTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculties/", faculty, Faculty.class))
                .isEqualTo(faculty);
    }

    @Test
    public void editFacultyTest() throws Exception {
        Faculty newFaculty = new Faculty(gryffindorFaculty.getId(), "Not Gryffindor", "Black");
        this.restTemplate.put("http://localhost:" + port + "/faculties/", newFaculty);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/1", Faculty.class))
                .isEqualTo(newFaculty);
    }
    @Test
    public void deleteFacultyTest() throws Exception {
        ResponseEntity<Faculty> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculties/2",
                HttpMethod.DELETE,
                new HttpEntity<>(faculty),
                Faculty.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNull();
    }
}
