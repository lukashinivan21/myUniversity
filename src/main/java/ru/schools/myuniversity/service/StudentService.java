package ru.schools.myuniversity.service;

import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.model.FieldsForQuery;
import ru.schools.myuniversity.model.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void deleteStudent(Long id);

    List<Student> findStudentsByAge(int age);

    List<Student> findStudentsByAgeBetween(int age1, int age2);

    List<Student> findStudentsByFacultyId(Long id);

    List<Student> findStudentsByFacultyName(String name);

    List<Student> findStudentsByFacultyColor(String color);

    Faculty getFacultyOfStudentWithId(Long id);

    int getAmountOfStudents();

    float getMiddleAgeOfStudents();

    List<FieldsForQuery> lastFiveStudents();

    List<Student> getStudentsByName(String name);

    List<String> studentsWithNameStartsWithLetter(String letter);

    double middleAgeOfStudentsByStream();
}
