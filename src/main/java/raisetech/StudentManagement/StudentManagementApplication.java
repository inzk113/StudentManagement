package raisetech.StudentManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  //  private Map<String, String> students = new HashMap<>();
//
//  public StudentManagementApplication() {
//    students.put("27", "Mayu Inuzuka");
//    students.put("30", "taro Yamada");
//    students.put("25", "Hanako Sato");
//
//  }
//
//  public static void main(String[] args) {
//    SpringApplication.run(StudentManagementApplication.class, args);
//  }
//
//  @GetMapping("/students")
//  public Map<String, String> getStudentInfo() {
//    return students;
//  }
//
//  @PostMapping("/students")
//  public String addStudent(@RequestParam String age, @RequestParam String name) {
//    students.put(age, name);
//    return "Student added successfully";
//
//  }
//}
  @Autowired
  private StudentRepository repository;
  private String name = "Enami Kouji";
  private String age = "37";

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    Student student = repository.searchByName("InuzukaMayu");
    return student.getName() + " " + student.getAge() + "歳";
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(String name, String age) {
    this.name = name;
    this.age = age;
  }

  @PostMapping("/studentName")
  public void updateStudentName(String name) {
    this.name = name;
  }
}