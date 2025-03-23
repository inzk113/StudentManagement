package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());
    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    Student student = new Student();
    student.setId("1");
    student.setName("MayuInuzuka");
    student.setNickName("INU");
    student.setEmail("inu@email.com");
    student.setCity("aich");
    student.setAge(27);
    student.setGender("female");

    String studentJson = objectMapper.writeValueAsString(student);

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(studentJson)) // JSONをリクエストボディに設定
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {
    Student student = new Student();
    student.setId("1");
    student.setName("MayuInuzuka");
    student.setNickName("INU");
    student.setEmail("inu@email.com");
    student.setCity("aich");
    student.setAge(27);
    student.setGender("female");
    String studentJson = objectMapper.writeValueAsString(student);

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(studentJson)) // JSONをリクエストボディに設定
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("MayuInuzuka");
    student.setNickName("INU");
    student.setEmail("inu@email.com");
    student.setCity("aich");
    student.setAge(27);
    student.setGender("female");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("MayuInuzuka");
    student.setNickName("INU");
    student.setEmail("inu@email.com");
    student.setCity("aich");
    student.setAge(27);
    student.setGender("female");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }

  @Test
  void 受講生IDを指定して受講生詳細が取得できること() throws Exception {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("MayuInuzuka");
    student.setNickName("INU");
    student.setEmail("inu@email.com");
    student.setCity("aich");
    student.setAge(27);
    student.setGender("female");

    String studentJson = objectMapper.writeValueAsString(student);

    mockMvc.perform(get("/student/{id}", "1"))
        .andExpect(status().isOk());
  }

  @Test
  void 存在しないIDを指定した場合に404エラーが返ること() throws Exception {
    mockMvc.perform(get("/student/999"))
        .andExpect(status().isNotFound());
  }
}



