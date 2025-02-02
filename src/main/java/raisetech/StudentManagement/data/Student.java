package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;
  private String name;
  private String nickName;
  private String email;
  private String city;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

}

