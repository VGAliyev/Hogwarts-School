package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("students/")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PostMapping(value = "{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big!");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.getStudentsByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("age-between/{ageMin}/{ageMax}")
    public ResponseEntity<Collection<Student>> getStudentByAgeBetween(@PathVariable int ageMin,
                                                                      @PathVariable int ageMax) {
        if (ageMin > 0 && ageMax > ageMin) {
            return ResponseEntity.ok(studentService.getStudentsByAgeBetween(ageMin, ageMax));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("{id}/faculty")
    public Faculty faculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("count")
    public Integer getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("average-age")
    public Float getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("last-five")
    public List<Student> getLast5Students() {
        return studentService.getLast5Students();
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if(foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-sorted-list-names-A")
    public List<String> getSortedListOfStudentNamesStartingWithLetterA() {
        return studentService.getSortedListNamesA();
    }

    @GetMapping("get-average-age-stream")
    public Float getAverageAgeStream() {
        return studentService.getAverageAgeStream();
    }
}
