package ru.schools.myuniversity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.schools.myuniversity.model.FieldsForQuery;
import ru.schools.myuniversity.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentsByAge(int age);

    List<Student> findStudentsByAgeBetween(int age1, int age2);

    List<Student> findStudentsByFaculty_Id(Long id);

    List<Student> findStudentsByFaculty_NameIgnoreCase(String name);

    List<Student> findStudentsByFaculty_ColorIgnoreCase(String color);

    @Query(value = "SELECT COUNT(name) FROM student", nativeQuery = true)
    int getAmountOfStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    float getMiddleAgeOfStudents();

    @Query(value = "SELECT name, age FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<FieldsForQuery> lastFiveStudents();

    List<Student> findStudentsByName(String name);
}
