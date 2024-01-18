package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long id = 0L;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++id);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(long id) {
        return faculties.get(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return Map.copyOf(faculties).values().stream().toList();
    }

    public Faculty editFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        ArrayList<Faculty> facultiesByColorList = new ArrayList<>();
        for (Faculty faculty: faculties.values()) {
            if (faculty.getColor().equals(color)) {
                facultiesByColorList.add(faculty);
            }
        }
        return facultiesByColorList;
    }
}
