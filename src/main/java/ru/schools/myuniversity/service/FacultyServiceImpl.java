package ru.schools.myuniversity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.repositories.FacultyRepository;

import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was requested method for create factory");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Search faculty with id " + id);
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null) {
            logger.warn("There is not faculty with id " + id);
        }
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was requested method for update faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Deleting faculty with id " + id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findFacultyByColor(String color) {
        logger.debug("Requesting faculty with color " + color);
        Faculty faculty = facultyRepository.findFacultyByColor(color);
        if (faculty == null) {
            logger.warn("There is not faculty with color " + color);
        }
        return faculty;
    }

    @Override
    public Faculty findFacultyByNameOrColor(String name, String color) {
        logger.debug("Requesting faculty with name " + name + " or with color " + color);
        Faculty faculty = facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
        if (faculty == null) {
            logger.warn("There is not faculty with name " + name + " or with color " + color);
        }
        return faculty;
    }

    public String longestNameOfFaculty() {
        String facultyName = null;
        Optional<String> result = facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length));
        if (result.isPresent()) {
            facultyName = result.get();
        }
        return facultyName;
    }
}
