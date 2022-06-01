package ru.schools.myuniversity.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        if (faculty.getId() != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public Faculty findFaculty(@PathVariable Long id) {
        Faculty result = facultyService.getFaculty(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        Faculty result = facultyService.updateFaculty(faculty);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/facultyWithColor{color}")
    public Faculty facultyWithColor(@PathVariable String color) {
        Faculty result = facultyService.findFacultyByColor(color);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/facultyWithNameOrColor")
    public Faculty facultyWithNameOrColor(@RequestParam("name") String name, @RequestParam("color") String color) {
        Faculty result = facultyService.findFacultyByNameOrColor(name, color);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/longestFacultyName")
    public String longestNameOfFaculty() {
        String name = facultyService.longestNameOfFaculty();
        if (name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return name;
    }
}
