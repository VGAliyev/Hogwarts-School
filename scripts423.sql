SELECT student.name, student.age, faculty.name FROM student INNER JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age FROM student INNER JOIN avatar ON avatar.student_id = student.id;