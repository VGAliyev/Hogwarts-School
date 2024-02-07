package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.Impl.AvatarServiceImpl;
import ru.hogwarts.school.service.Impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.constants.TestConstants.HARRY;
import static ru.hogwarts.school.constants.TestConstants.JOHN;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void addStudent() throws Exception {
        Long studentId = 1L;
        String studentName = "John";
        int studentAge = 20;

        Student student = new Student(studentId, studentName, studentAge);

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", studentId);
        studentObject.put("name", studentName);
        studentObject.put("age", studentAge);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/students/")
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(studentName))
                .andExpect(jsonPath("$.age").value(studentAge));
    }

    @Test
    public void uploadAvatar() throws Exception {
        byte[] avatar = new byte[1024];
        // https://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
        MockMultipartFile file = new MockMultipartFile("avatar", "avatar.png", "image/png", avatar);

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/students/{id}/avatar", 1)
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudent() throws Exception {
        when(studentRepository.findById(any())).thenReturn(Optional.of(JOHN));

        mockMvc.perform(MockMvcRequestBuilders.get("/students/1", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void getAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(List.of(JOHN));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").exists())
                .andExpect(jsonPath("$.[*].name").value("John"));
    }

    @Test
    public void getStudentsByAge() throws Exception {
        when(studentRepository.findByAge(anyInt())).thenReturn(List.of(JOHN));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/age/{age}", 20)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value("John"))
                .andExpect(jsonPath("$.[*].age").value(20));
    }

    @Test
    public void getStudentByAgeBetween() throws Exception {
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(JOHN));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/age-between/15/25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value("John"))
                .andExpect(jsonPath("$.[*].age").value(20));
    }

    @Test
    public void faculty() throws Exception {
        when(studentRepository.findById(any())).thenReturn(Optional.of(HARRY));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/{id}/faculty", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    public void editStudent() throws Exception {
        Long studentId = 1L;
        String studentName = "Not John";
        int studentAge = 22;
        Student student = new Student(studentId, studentName, studentAge);
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", studentId);
        studentObject.put("name", studentName);
        studentObject.put("age", studentAge);

        when(studentRepository.findById(any())).thenReturn(Optional.of(JOHN));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/students/")
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Not John"));
    }

    @Test
    public void deleteStudent() throws Exception {
        when(studentRepository.findById(any())).thenReturn(Optional.of(JOHN));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/students/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
