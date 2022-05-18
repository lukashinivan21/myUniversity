package ru.schools.myuniversity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.schools.myuniversity.controllers.StudentController;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.model.Student;
import ru.schools.myuniversity.repositories.FacultyRepository;
import ru.schools.myuniversity.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.schools.myuniversity.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    Faculty testFaculty = new Faculty();

    @BeforeEach
    public void setBefore() {
        testFaculty.setName(facultyName);
        testFaculty.setColor(facultyColor);
        facultyRepository.save(testFaculty);
        studentForCreateTest.setFaculty(testFaculty);
        STUDENT2.setFaculty(testFaculty);
        STUDENT3.setFaculty(testFaculty);
        STUDENT4.setFaculty(testFaculty);
        studentRepository.save(STUDENT2);
        studentRepository.save(STUDENT3);
        studentRepository.save(STUDENT4);
    }

    @AfterEach
    public void deleteAfter() {
        List<Student> students = studentRepository.findAll();
        List<Long> numbers = new ArrayList<>();
        for (Student student : students) {
            if (student.getId() > lastIdFromDataBase) {
                numbers.add(student.getId());
            }
        }
        for (Long number : numbers) {
            studentRepository.deleteById(number);
        }
        List<Faculty> faculties = facultyRepository.findAll();
        facultyRepository.deleteById(faculties.get(faculties.size() - amountOfAddedFaculty).getId());
    }

    @Test
    public void checkController() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void checkRepository() {
        Assertions.assertThat(studentRepository).isNotNull();
        Assertions.assertThat(facultyRepository).isNotNull();
    }

    @Test
    public void testCreateStudent() {
        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student", studentForCreateTest, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(studentForCreateTest.getName());
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(studentForCreateTest.getAge());
    }

    @Test
    public void testGetStudent() {
        List<Student> students = studentRepository.findAll();
        long id = students.get(students.size() - index1ForTests).getId();
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:" + port + "/student/" + id, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(STUDENT4.getName());
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(STUDENT4.getAge());
    }

    @Test
    public void checkUpdateStudent() {
        List<Student> students = studentRepository.findAll();
        long id = students.get(students.size() - index2ForTests).getId();
        studentForCreateTest.setId(id);
        HttpEntity<Student> entity = new HttpEntity<>(studentForCreateTest);
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student/", HttpMethod.PUT, entity, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(studentForCreateTest.getName());
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(studentForCreateTest.getAge());
    }

    @Test
    public void checkDeleteStudent() {
        List<Student> students = studentRepository.findAll();
        long id = students.get(students.size() - index3ForTests).getId();
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student/" + id, HttpMethod.DELETE, null, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void checkStudentsWithAge() {
        List<Student> students = studentRepository.findAll();
        int age = students.get(students.size() - index2ForTests).getAge();
        int size = 1;
        ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:" + port + "/student/studentsWithAge" + age,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(size);
        Assertions.assertThat(response.getBody().get(0).getName()).isEqualTo(STUDENT3.getName());
        Assertions.assertThat(response.getBody().get(0).getAge()).isEqualTo(STUDENT3.getAge());
    }

    @Test
    public void checkStudentsWithAgeBetween() {
        List<Student> students = studentRepository.findAll();
        int age1 = students.get(students.size() - index1ForTests).getAge();
        int age2 = students.get(students.size() - index3ForTests).getAge();
        ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:" + port +
                        "/student/studentsWithAgeBetween?age1=" + age1 + "&age2=" + age2, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(size);
        Assertions.assertThat(response.getBody().get(index0ForTests).getName()).isEqualTo(STUDENT2.getName());
        Assertions.assertThat(response.getBody().get(index1ForTests).getAge()).isEqualTo(STUDENT3.getAge());
        Assertions.assertThat(response.getBody().get(index2ForTests).getName()).isEqualTo(STUDENT4.getName());
        Assertions.assertThat(response.getBody().get(index2ForTests).getAge()).isEqualTo(STUDENT4.getAge());
    }

    @Test
    public void checkFacultyOfStudentWithId() {
        List<Student> students = studentRepository.findAll();
        long id = students.get(students.size() - index1ForTests).getId();
        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/student/facultyOfStudentWithId" + id, Faculty.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(testFaculty.getName());
        Assertions.assertThat(response.getBody().getColor()).isEqualTo(testFaculty.getColor());
    }

    @Test
    public void checkStudentsOfFaculty() {
        String color = testFaculty.getColor();
        ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:" + port +
                "/student/studentsOfFaculty?color=" + color, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().get(response.getBody().size() - index1ForTests).getName()).isEqualTo(STUDENT4.getName());
        Assertions.assertThat(response.getBody().get(response.getBody().size() - index1ForTests).getAge()).isEqualTo(STUDENT4.getAge());
        Assertions.assertThat(response.getBody().get(response.getBody().size() - index2ForTests).getName()).isEqualTo(STUDENT3.getName());
        Assertions.assertThat(response.getBody().get(response.getBody().size() - index2ForTests).getAge()).isEqualTo(STUDENT3.getAge());
    }
}
