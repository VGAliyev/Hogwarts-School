package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    private final Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
    private final Student student = new Student(1L, "Harry Potter", 15, faculty);

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoadsTest() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/", Collection.class).size())
                .isEqualTo(3);
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/1", Student.class))
                .isEqualTo(student);
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/age/16", Collection.class).size())
                .isEqualTo(1);
    }

    @Test
    public void getStudentsByAgeBetweenTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/age-between/10/17", Collection.class).size())
                .isEqualTo(2);
    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/1/faculty", Faculty.class))
                .isEqualTo(faculty);
    }

    @Test
    public void addStudentTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/students/", student, Student.class))
                .isEqualTo(student);
    }

    @Test
    public void addStudentAvatarTest() throws Exception {
        byte[] avatar = new byte[1024];
        long id = 1;
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.put("avatar", Collections.singletonList(new ByteArrayResource(avatar)));
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                RequestEntity.post("/{id}/avatar", id)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body),
                String.class
        );
    }

    @Test
    public void editStudentTest() throws Exception {
        Student newStudent = new Student(student.getId(), "Not Harry Potter", 20, faculty);
        this.restTemplate.put("http://localhost:" + port + "/students/", newStudent);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/1", Student.class))
                .isEqualTo(newStudent);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/students/1",
                HttpMethod.DELETE,
                new HttpEntity<>(student),
                Student.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNull();
    }
}
