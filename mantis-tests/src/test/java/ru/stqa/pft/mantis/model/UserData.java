package ru.stqa.pft.mantis.model;

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
