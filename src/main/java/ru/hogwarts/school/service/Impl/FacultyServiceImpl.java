package ru.hogwarts.school.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        logger.info("Was invoked method for get faculty by id {}", id);
        return facultyRepository.findById(id).get();
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.warn("Was invoked method for edit faculty. Attention: faculty edited!");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        logger.warn("Was invoked method for delete faculty by id {}. Attention: faculty deleted!", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.debug("Was invoked method for get faculty by color {}", color);
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> getFacultiesByNameOrColor(String name, String color) {
        logger.debug("Was invoked method for get faculty by name {} or color {} ignore case", name, color);
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElseThrow(() -> {
                    logger.error("Students in faculty by id {} not found!", id);
                    return new RuntimeException();
                });
    }
}
