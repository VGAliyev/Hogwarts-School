package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    @Query(value = "SELECT COUNT(name) FROM student", nativeQuery = true)
    Integer getStudentsCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Float getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLast5Students();
}
