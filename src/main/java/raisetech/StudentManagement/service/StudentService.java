package raisetech.StudentManagement.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  /**
   * 受講生情報を取り扱うサービスです。 検索や登録・更新処理を行います。
   */
  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索を行います。 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生一覧（全件）
   */
  public List<StudentDetail> serchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCoursesList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索です。 IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 　受講生ID
   * @return　受講生詳細
   */
  public StudentDetail findStudentById(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講コース情報を個別に登録し、受講コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 　受講生コース情報
   * @param student       　受講生
   */
  private static void initStudentsCourse(StudentCourse studentCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setStartDate(Timestamp.valueOf(now));
    studentCourse.setEndDate(Timestamp.valueOf(now.plusYears(1)));
  }

  /**
   * 受講生詳細の更新を行います。 受講生詳細と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}