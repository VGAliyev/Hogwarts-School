package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long id = 0L;

    @Override
    public Student addStudent(Student student) {
        student.setId(id++);
        students.put(student.getId(), student);
        return  student;
    }

    @Override
    public Student getStudent(long id) {
        return students.get(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return Map.copyOf(students).values().stream().toList();
    }

    @Override
    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    @Override
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
