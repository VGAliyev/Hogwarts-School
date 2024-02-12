package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public class TestConstants {
    public final static Faculty GRYFFINDOR = new Faculty(1L, "Gryffindor", "Red");
    public final static Faculty SLYTHERIN = new Faculty(2L, "Slytherin", "Green");
    public final static Student HARRY = new Student(1L, "Harry Potter", 11);
    public final static Student HERMIONE = new Student(2L, "Hermione Granger", 12);
}
