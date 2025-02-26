package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST　APIとして実行されるcontrollerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です 全件検索を行うので条件指定は行いません。
   *
   * @return　受講生詳細一覧（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧検索します。",
      //summary=一覧 description=詳細
      responses = {
          @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = User.class))),
          @ApiResponse(responseCode = "500", description = "サーバーエラー")})

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentsList() {
    return service.serchStudentList();
  }

  /**
   * 受講生詳細検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 　受講生ID
   * @return　受講生詳細
   */
  @Operation(summary = "受講生検索", description = "指定したIDの受講生詳細情報を取得します。",
      parameters = {
          @Parameter(name = "id", description = "受講生ID", required = true, example = "123")},
      responses = {
          @ApiResponse(responseCode = "200", description = "情報の取得に成功しました。",
              content = @Content(schema = @Schema(implementation = StudentDetail.class))),
          @ApiResponse(responseCode = "400", description = "無効なIDです"),
          @ApiResponse(responseCode = "400", description = "受講生が見つかりません")
      }
  )

  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.findStudentById(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生登録", description = "新しく受講生を登録します",
      responses = {
          @ApiResponse(responseCode = "201", description = "登録処理が成功しました。",
              content = @Content(schema = @Schema(implementation = StudentDetail.class))),
          @ApiResponse(responseCode = "400", description = "無効なリクエストです")
      }
  )
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent
  (@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います。（論理削除）
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(
      summary = "受講生詳細の更新",
      description = "指定した受講生の情報を更新します。",
      responses = {
          @ApiResponse(responseCode = "200", description = "更新成功しました。",
              content = @Content(schema = @Schema(implementation = String.class))),
          @ApiResponse(responseCode = "400", description = "無効なリクエストです"),
          @ApiResponse(responseCode = "404", description = "受講生が見つかりません")
      }
  )
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が完了しました！");
  }
}

//