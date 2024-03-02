package ru.hogwarts.school.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private final Object obj = new Object();

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(long id) {
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id).get();
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    @Override
    public Student editStudent(Student student) {
        logger.warn("Was invoked method for edit student. Attention: student edited!");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        logger.warn("Was invoked method for delete student by id {}. Attention: student deleted!", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        logger.debug("Was invoked method for get student by age: {} years", age);
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(int ageMin, int ageMax) {
        logger.debug("Was invoked method for get student by age between: {} and {} years", ageMin, ageMax);
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    @Override
    public Faculty getFaculty(Long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElseThrow(() -> {
                    logger.error("Faculty of student by id {} not found!", id);
                    return new RuntimeException();
                });
    }

    @Override
    public Integer getStudentsCount() {
        logger.info("Was invoked method for get students count");
        return studentRepository.getStudentsCount();
    }

    @Override
    public Float getAverageAge() {
        logger.info("Was invoked method for get students average age");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLast5Students() {
        logger.info("Was invoked method for get five last students");
        return studentRepository.getLast5Students();
    }

    @Override
    public List<String> getSortedListNamesA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.charAt(0) == 'A')
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Float getAverageAgeStream() {
        return (float) studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(Double.NaN);
    }

    @Override
    public void printParallel() {
        List<String> studentNames = getStudentNames();
        System.out.println("First: " + studentNames.get(0) + ". Second: " + studentNames.get(1));
        new Thread(() -> {
            System.out.println("Third: " + studentNames.get(2) + ". Fourth: " + studentNames.get(3));
        }).start();
        new Thread(() -> {
            System.out.println("Fifth: " + studentNames.get(4) + ". Sixth: " + studentNames.get(5));
        }).start();
    }

    @Override
    public void printSynchronized() {
        List<String> studentNames = getStudentNamesSynchronized();
        System.out.println("First: " + studentNames.get(0) + ". Second: " + studentNames.get(1));
        new Thread(() -> {
            System.out.println("Third: " + studentNames.get(2) + ". Fourth: " + studentNames.get(3));
        }).start();
        new Thread(() -> {
            System.out.println("Fifth: " + studentNames.get(4) + ". Sixth: " + studentNames.get(5));
        }).start();
    }

    private List<String> getStudentNames() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    private List<String> getStudentNamesSynchronized() {
        synchronized (obj) {
            return studentRepository.findAll().stream()
                    .map(Student::getName)
                    .collect(Collectors.toList());
        }
    }
}
