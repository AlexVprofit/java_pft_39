package ru.stqa.pft.mantis.model;
/*
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "mantis_user_table")
public class UserData {
  @Id
  @Column(name = "id")
  private int id;
  @Expose
  @Column (name = "username")
  @Type(type = "text")
  private  String username;
  @Expose
  @Column (name = "email")
  private  String email;


  public String getUsername(){
    return username;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
*/
public class UserData {

  public  int id=0;
  public  String login;

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserData userData = (UserData) o;

    if (id != userData.id) return false;
    return login != null ? login.equals(userData.login) : userData.login == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    return result;
  }

  public UserData withLogin(String  login) {
    this.login= login;
    return this;
  }

  public String getLogin() {
    return login;
  }

}
