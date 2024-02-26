-- liquibase formatted sql

-- changeset v.aliyev:1
CREATE INDEX student_name_index ON student (name);

-- changeset v.aliyev:2
CREATE INDEX faculty_name_color ON faculty (name, color);