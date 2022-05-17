package ru.schools.myuniversity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.schools.myuniversity.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentsByAge(int age);

    List<Student> findStudentsByAgeBetween(int age1, int age2);

    List<Student> findStudentsByFaculty_Id(Long id);

    List<Student> findStudentsByFaculty_NameIgnoreCase(String name);

    List<Student> findStudentsByFaculty_ColorIgnoreCase(String color);
}
