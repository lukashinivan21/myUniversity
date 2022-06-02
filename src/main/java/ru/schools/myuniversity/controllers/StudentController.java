package ru.schools.myuniversity.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.model.FieldsForQuery;
import ru.schools.myuniversity.model.Student;
import ru.schools.myuniversity.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        if (student.getId() != 0 || student.getFaculty().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student findStudent(@PathVariable Long id) {
        Student foundStudent = studentService.getStudent(id);
        if (foundStudent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return foundStudent;
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return updatedStudent;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/studentsWithAge{age}")
    public ResponseEntity<List<Student>> studentsWithAge(@PathVariable int age) {
        List<Student> result = studentService.findStudentsByAge(age);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/studentsWithAgeBetween")
    public List<Student> studentsWithAgeBetween(@RequestParam("age1") int age1,@RequestParam("age2")  int age2) {
        List<Student> result = studentService.findStudentsByAgeBetween(age1, age2);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/studentsOfFaculty")
    public List<Student> studentsOfFaculty(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String color) {
        List<Student> result = null;
        if (id != null) {
            result = studentService.findStudentsByFacultyId(id);
        }
        if (name != null) {
            result = studentService.findStudentsByFacultyName(name);
        }
        if (color != null) {
            result = studentService.findStudentsByFacultyColor(color);
        }
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/facultyOfStudentWithId{id}")
    public Faculty facultyOfStudentWithId(@PathVariable Long id) {
        Faculty result = studentService.getFacultyOfStudentWithId(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @GetMapping("/amountOfStudents")
    public int amountOfStudents() {
        return studentService.getAmountOfStudents();
    }

    @GetMapping("/middleAge")
    public float middleAgeOfStudent() {
        return studentService.getMiddleAgeOfStudents();
    }

    @GetMapping("/lastFiveStudents")
    public ResponseEntity<List<FieldsForQuery>> lastFiveStudent() {
        return ResponseEntity.ok(studentService.lastFiveStudents());
    }

    @GetMapping("/studentsWithName{name}")
    public ResponseEntity<List<Student>> getStudentsByName(@PathVariable String name) {
        List<Student> result = studentService.getStudentsByName(name);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/studentsWithNameStartsWithLetter{letter}")
    public ResponseEntity<List<String>> studentsWithNameStartsWithLetter(@PathVariable String letter) {
        List<String> result = studentService.studentsWithNameStartsWithLetter(letter);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/middleAgeByStream")
    public double middleAgeOfStudentsByStream() {
        double result =  studentService.middleAgeOfStudentsByStream();
        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return result;
    }



    @GetMapping("/printStudentsNames")
    public ResponseEntity<String> printStudentsNames() {
        studentService.printStudentsNames();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/printNamesInOrder")
    public ResponseEntity<String> printStudentsNamesInOrder() {
        studentService.printStudentsNamesInOrder();
        return ResponseEntity.ok().build();
    }


}
