package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Students> searchStudent();

  @Select("SELECT * FROM students_courses")
  List<Students_courses> searchStudents_courses();

}





