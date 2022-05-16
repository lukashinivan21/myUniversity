package ru.schools.myuniversity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.schools.myuniversity.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findFacultyByColor(String color);

    Faculty findFacultyByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
