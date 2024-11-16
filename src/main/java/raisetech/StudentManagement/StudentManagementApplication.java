package raisetech.StudentManagement;


import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

//  private String name = "Enami Kouji";
//  private String age ="37";

  private Map<String, String> students = new HashMap<>();

  public StudentManagementApplication() {
    students.put("27", "Mayu Inuzuka");
    students.put("30", "taro Yamada");
    students.put("25", "Hanako Sato");

  }

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);

    System.out.println("こんにちは");
  }

  @GetMapping("/students")
  public Map<String, String> getStudentInfo() {
    return students;
  }

  @PostMapping("/students")
  public String addStudent(@RequestParam String age, @RequestParam String name) {
    students.put(age, name);
    return "Student added successfully";

  }
}

//  public static void main(String[] args) {
//    SpringApplication.run(StudentManagementApplication.class, args);
//  }

//  @GetMapping("/studentInfo")
//  public String getStudentInfo() {
//    return name + " "+ age + "歳";
//  }
//
//  @PostMapping("/studentInfo")
//  public void setStudentInfo(String name,String age) {
//    this.name = name;
//    this.age =age;
//  }
//
//  @PostMapping("/studentName")
//  public void updateStudentName(String name){
//    this.name = name;
//  }
//}