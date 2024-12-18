package raisetech.StudentManagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Students;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;

  }

  public List<Students> searchStudentList() {
    repository.searchStudent();

    return repository.searchStudent();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }


  public List<StudentsCourses> searchJavaCoursesList() {

    List<StudentsCourses> searchStudentsCourses = repository.searchStudentsCourses();

    return searchStudentsCourses.stream()
        .filter(course -> course.getCourseName() != null && course.getCourseName().toLowerCase()
            .contains("java"))
        .collect(Collectors.toList());

  }

  public List<Students> search30sStudentsList() {
    return repository.searchStudent().stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39).toList();
  }
}

