package ru.schools.myuniversity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.model.FieldsForQuery;
import ru.schools.myuniversity.model.Student;
import ru.schools.myuniversity.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student createStudent(Student student) {
        logger.info("Was requested method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Search student with id " + id);
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            logger.warn("There is not student with id " + id);
        }
        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Was requested method for update student");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Deleting student with id " + id);
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findStudentsByAge(int age) {
        logger.info("Was requested method for finding students by age " + age);
        return checkListOnNull(studentRepository.findStudentsByAge(age));
    }

    @Override
    public List<Student> findStudentsByAgeBetween(int age1, int age2) {
        logger.info("Was requested method for finding students by age between " + age1 + " and " + age2);
        return checkListOnNull(studentRepository.findStudentsByAgeBetween(age1, age2));
    }

    @Override
    public List<Student> findStudentsByFacultyId(Long id) {
        logger.info("Was requested method for finding students by faculty id " + id);
        return checkListOnNull(studentRepository.findStudentsByFaculty_Id(id));
    }

    @Override
    public List<Student> findStudentsByFacultyName(String name) {
        logger.info("Was requested method for finding students by faculty name " + name);
        return checkListOnNull(studentRepository.findStudentsByFaculty_NameIgnoreCase(name));
    }

    @Override
    public List<Student> findStudentsByFacultyColor(String color) {
        logger.info("Was requested method for finding students by faculty color " + color);
        return checkListOnNull(studentRepository.findStudentsByFaculty_ColorIgnoreCase(color));
    }

    @Override
    public Faculty getFacultyOfStudentWithId(Long id) {
        logger.info("Was requested method for finding faculty of student with id " + id);
        Student result = studentRepository.findById(id).orElse(null);
        if (result == null) {
            logger.warn("There is not faculty of student with id " + id);
            return null;
        }
        return result.getFaculty();
    }

    public int getAmountOfStudents() {
        logger.debug("Request for quantity of students");
        return studentRepository.getAmountOfStudents();
    }

    public float getMiddleAgeOfStudents() {
        logger.debug("Request for middle age of students");
        return studentRepository.getMiddleAgeOfStudents();
    }

    public List<FieldsForQuery> lastFiveStudents() {
        logger.debug("Request for last five students in the list");
        return studentRepository.lastFiveStudents();
    }

    public List<Student> getStudentsByName(String name) {
        logger.info("Was request method for finding students with name " + name);
        return checkListOnNull(studentRepository.findStudentsByName(name));
    }

    private List<Student> checkListOnNull(List<Student> checkedList) {
        if (checkedList.isEmpty()) {
            logger.warn("There are not students with this parameters");
            return null;
        }
        return checkedList;
    }


}
