package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long id = 0L;

    public Student addStudent(Student student) {
        student.setId(++id);
        students.put(student.getId(), student);
        return  student;
    }

    public Student getStudent(long id) {
        return students.get(id);
    }

    public Collection<Student> getAllStudents() {
        return Map.copyOf(students).values().stream().toList();
    }

    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        ArrayList<Student> studentsByAgeList = new ArrayList<>();
        for (Student student: students.values()) {
            if (student.getAge() == age) {
                studentsByAgeList.add(student);
            }
        }
        return studentsByAgeList;
    }
}
