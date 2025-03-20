package raisetech.StudentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;
  @Valid
  private List<StudentCourse> studentCourseList;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true; // 同じオブジェクトならOK
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;// 型が違うならダメ
    }

    StudentDetail studentDetail = (StudentDetail) obj;
    return Objects.equals(this.student, studentDetail.student) &&
        Objects.equals(this.studentCourseList, studentDetail.studentCourseList);

  }

  @Override
  public int hashCode() {
    return Objects.hash(student, studentCourseList);
  }
}


