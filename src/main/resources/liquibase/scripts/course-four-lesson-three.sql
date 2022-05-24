- liquibase formatted sql

- changeSet ilukashin:1
CREATE INDEX students_name_index ON student (name);

- changeSet ilukashin:2
CREATE INDEX faculty_name_color_index ON faculty (color, name);

