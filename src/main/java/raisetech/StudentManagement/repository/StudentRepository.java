package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Students;
import raisetech.StudentManagement.data.StudentsCourses;


/**
 * 受講生情報を扱うリポジトリ。
 * <p>
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   */

  @Select("SELECT * FROM students")
  List<Students> searchStudent();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Select("SELECT*FROM students_courses")
  List<StudentsCourses> searchJavaCourses();

  @Select("SELECT*FROM students")
  List<Students> search30sStudents();

}





