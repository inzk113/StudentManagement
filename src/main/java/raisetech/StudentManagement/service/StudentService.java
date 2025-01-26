package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {


  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

//  public List<Student> serchStudentList() {
//    return repository.search();
//  }



  public StudentDetail findStudentById(String id) {
    Student student = repository.searchstudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  public List<Student> serchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCoursesList();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {

      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setStartDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
      studentsCourse.setEndDate(java.sql.Timestamp.valueOf(LocalDateTime.now().plusYears(1)));

      repository.registerStudentsCourses(studentsCourse);
    }
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentCourses(studentsCourse);
    }
  }
}