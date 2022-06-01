package ru.schools.myuniversity.service;

import ru.schools.myuniversity.model.Faculty;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void deleteFaculty(Long id);

    Faculty findFacultyByColor(String color);

    Faculty findFacultyByNameOrColor(String name, String color);

    String longestNameOfFaculty();

}
