package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
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
  List<Student> search();

  //search()
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  //serchstudent()
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCourses(String studentId);


  //IDは自動採番にするからいらない
  @Insert("INSERT INTO students (name, age,city,email, nick_name,gender,remark,is_deleted) "
      + " VALUES (#{name}, #{age},#{city}, #{email}, #{nickName},#{gender},#{remark},false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);


  @Insert("INSERT INTO students_courses(student_id,course_name,start_date,end_date) "
      + "VALUES(#{studentId},#{courseName},#{startDate},#{endDate})")

  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);


  @Update(
      "UPDATE students SET name = #{name}, nick_name = #{nickName}, email = #{email}, city = #{city}, "
          + "age = #{age}, gender = #{gender}, remark = #{remark} WHERE id = #{id}")
  void updateStudent(Student student);


  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id} ")
  void updateStudentCourses(StudentsCourses studentsCourses);
}

//受講生更新のHTMLを作成しましょう
//（受講生登録を参考にしましょう）
//registerのコピペでいい　
//studentListの名前のところに「エータグ」をやるとリンクができる
//@GetMapping("/updateStudent)

//受講生一覧の表で名前をクリックすると、その受講生のID情報に
//もとづいたデータを表示できるようにしましょう





