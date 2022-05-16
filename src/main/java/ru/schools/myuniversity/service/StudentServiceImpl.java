package ru.schools.myuniversity.service;

import org.springframework.stereotype.Service;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.model.Student;
import ru.schools.myuniversity.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findStudentsByAge(int age) {
        return checkListOnNull(studentRepository.findStudentsByAge(age));
    }

    @Override
    public List<Student> findStudentsByAgeBetween(int age1, int age2) {
        return checkListOnNull(studentRepository.findStudentsByAgeBetween(age1, age2));
    }

    @Override
    public List<Student> findStudentsByFacultyId(Long id) {
        return checkListOnNull(studentRepository.findStudentsByFaculty_Id(id));
    }

    @Override
    public List<Student> findStudentsByFacultyName(String name) {
        return checkListOnNull(studentRepository.findStudentsByFaculty_NameIgnoreCase(name));
    }

    @Override
    public List<Student> findStudentsByFacultyColor(String color) {
        return checkListOnNull(studentRepository.findStudentsByFaculty_ColorIgnoreCase(color));
    }

    @Override
    public Faculty getFacultyOfStudentWithId(Long id) {
        Student result = studentRepository.findById(id).orElse(null);
        if (result == null) {
            return null;
        }
        return result.getFaculty();
    }

    private List<Student> checkListOnNull(List<Student> checkedList) {
        if (checkedList.isEmpty()) {
            return null;
        }
        return checkedList;
    }
}
