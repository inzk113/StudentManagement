package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧機能_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCoursesList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository).search();
    verify(repository).searchStudentCoursesList();
    verify(converter).convertStudentDetails(studentList, studentCourseList);

    //テスト対象のことは"sut"とつけることが多い。(service)テストの検証する対象"actual"(studentDetail)。

  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();
    List<StudentCourse> studentCourse = new ArrayList<>();
    String id = "100";
    student.setId(id);

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(studentCourse);

    StudentDetail expected = new StudentDetail(student, studentCourse);

    StudentDetail actual = sut.findStudentById(id);

    verify(repository).searchStudent(id);
    verify(repository).searchStudentCourse(id);
    assertEquals(expected, actual);
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {

    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository).registerStudent(student);
    verify(repository).registerStudentCourse(studentCourse);
  }


  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出せていること() {

    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository).updateStudent(student);//studentDetail.getStudent()??
    verify(repository).updateStudentCourse(studentCourse);
  }
}