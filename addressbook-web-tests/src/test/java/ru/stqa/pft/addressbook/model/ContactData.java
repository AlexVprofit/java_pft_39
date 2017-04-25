package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

// Аннотация для подсказки при формировании тега в файле типа xml
@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")

public class ContactData {
  //  private final HashSet<ContactData> delegate;
  @Transient
  protected Set<ContactData> delegate;


  @XStreamOmitField  //подсказка пропустить следующее поле (т.е. id) в XML
  @Id
  @Column(name = "id")

  private int id = Integer.MAX_VALUE;
  @Expose // это для формата файлов JSON и это указывает какие поля д.б. включены в файл
  @Column(name = "firstname")
  private String firstname;
  @Expose // это для формата файлов JSON и это указывает какие поля д.б. включены в файл
  @Column(name = "lastname")
  private String lastname;
  private String title;
  private String company;
  @Expose // это для формата файлов JSON и это указывает какие поля д.б. включены в файл
  @Column(name = "address")
  @Type(type = "text")
  private String new_adress;
  @Expose // это для формата файлов JSON и это указывает какие поля д.б. включены в файл
  @XStreamOmitField  //подсказка пропустить следующее поле  в XML
  @Column(name = "home")
  @Type(type = "text")
  private String telhome;
  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilPhone;
  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;
  @Transient   // подсказка пропустить поле для считывания из бд
  private String phone2Phone;
  @Expose // это для формата файлов JSON и это указывает какие поля д.б. включены в файл
  @Transient
  private String allEmails;
  @Expose // это для формата файлов JSON и это указывает какие поля д.б. включены в файл
  @Transient
  private String allPhones;
  @Transient
  private String allDetails;
  @Column(name = "email")
  @Type(type = "text")
  private String email1;
  @Type(type = "text")
  private String email2;
  @Type(type = "text")
  private String email3;
  
  @Transient
  private String group;

  public int getId() {
    return id;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public String getAllDetails() {
    return allDetails;
  }

  public ContactData withAllDetails(String allDetails) {
    this.allDetails = allDetails;
    return this;
  }


  public ContactData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withTitle(String title) {
    this.title = title;
    return this;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData withNew_adress(String new_adress) {
    this.new_adress = new_adress;
    return this;
  }

  public ContactData withTelHome(String telhome) {
    this.telhome = telhome;
    return this;
  }

  public ContactData withMobilePhone(String mobilPhone) {
    this.mobilPhone = mobilPhone;
    return this;
  }

  public ContactData withWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public ContactData withPhone2Phone(String phone2Phone) {
    this.phone2Phone = phone2Phone;
    return this;
  }

  public ContactData withEmail1(String email1) {
    this.email1 = email1;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }


  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getNew_adress() {
    return new_adress;
  }

  public String getTelHome() {
    return telhome;
  }

  public String getMobilPhone() {
    return mobilPhone;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public String getPhone2Phone() {
    return phone2Phone;
  }

  public String getEmail1() {
    return email1;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
  }

  public String getGroup() {
    return group;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", new_adress='" + new_adress + '\'' +
//            ", telhome='" + telhome + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstname != null && that.firstname != null) {
      if (!firstname.equals(that.firstname)) { return false; }
    }
    if (lastname != null && that.lastname != null) {
      if (!lastname.equals(that.lastname)) { return false; }
    }
    if (title != null && that.title != null) {
      if (!title.equals(that.title)) { return false; }
    }
    if (company != null && that.company != null) {
      if (!company.equals(that.company)) { return false; }
    }
    if (new_adress != null && that.new_adress != null) {
      if (!new_adress.equals(that.new_adress)) { return false; }
    }
    if (telhome != null && that.telhome != null) {
      if (!telhome.equals(that.telhome)) { return false; }
    }
    if (mobilPhone != null && that.mobilPhone != null) {
      if (!mobilPhone.equals(that.mobilPhone)) { return false; }
    }
    if (workPhone != null && that.workPhone != null) {
      if (!workPhone.equals(that.workPhone)) { return false; }
    }
    if (title != null && that.title != null) {
      if (!title.equals(that.title)) { return false; }
    }
    if (phone2Phone != null && that.phone2Phone != null) {
      if (!phone2Phone.equals(that.phone2Phone)) { return false; }
    }
    if (allEmails != null && that.allEmails != null) {
      if (!allEmails.equals(that.allEmails)) { return false; }
    }
    if (allPhones != null && that.allPhones != null) {
      if (!allPhones.equals(that.allPhones)) { return false; }
    }
    if (allDetails != null && that.allDetails != null) {
      if (!allDetails.equals(that.allDetails)) { return false; }
    }
    if (email1 != null && that.email1 != null) {
      if (!email1.equals(that.email1)) { return false; }
    }
    if (email2 != null && that.email2 != null) {
      if (!email2.equals(that.email2)) { return false; }
    }
    if (email3 != null && that.email3 != null) {
      if (!email3.equals(that.email3)) { return false; }
    }
    return group != null ? group.equals(that.group) : that.group == null;
/*
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (title != null ? !title.equals(that.title) : that.title != null) return false;
    if (company != null ? !company.equals(that.company) : that.company != null) return false;
    if (new_adress != null ? !new_adress.equals(that.new_adress) : that.new_adress != null) return false;
    if (telhome != null ? !telhome.equals(that.telhome) : that.telhome != null) return false;
    if (mobilPhone != null ? !mobilPhone.equals(that.mobilPhone) : that.mobilPhone != null) return false;
    if (workPhone != null ? !workPhone.equals(that.workPhone) : that.workPhone != null) return false;
    if (phone2Phone != null ? !phone2Phone.equals(that.phone2Phone) : that.phone2Phone != null) return false;
    if (allEmails != null ? !allEmails.equals(that.allEmails) : that.allEmails != null) return false;
    if (allPhones != null ? !allPhones.equals(that.allPhones) : that.allPhones != null) return false;
    if (allDetails != null ? !allDetails.equals(that.allDetails) : that.allDetails != null) return false;
    if (email1 != null ? !email1.equals(that.email1) : that.email1 != null) return false;
    if (email2 != null ? !email2.equals(that.email2) : that.email2 != null) return false;
    if (email3 != null ? !email3.equals(that.email3) : that.email3 != null) return false;
    return group != null ? group.equals(that.group) : that.group == null;
*/
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
/*
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (company != null ? company.hashCode() : 0);
    result = 31 * result + (new_adress != null ? new_adress.hashCode() : 0);
    result = 31 * result + (telhome != null ? telhome.hashCode() : 0);
    result = 31 * result + (mobilPhone != null ? mobilPhone.hashCode() : 0);
    result = 31 * result + (workPhone != null ? workPhone.hashCode() : 0);
    result = 31 * result + (phone2Phone != null ? phone2Phone.hashCode() : 0);
    result = 31 * result + (allEmails != null ? allEmails.hashCode() : 0);
    result = 31 * result + (allPhones != null ? allPhones.hashCode() : 0);
    result = 31 * result + (allDetails != null ? allDetails.hashCode() : 0);
    result = 31 * result + (email1 != null ? email1.hashCode() : 0);
    result = 31 * result + (email2 != null ? email2.hashCode() : 0);
    result = 31 * result + (email3 != null ? email3.hashCode() : 0);
    result = 31 * result + (group != null ? group.hashCode() : 0);
*/
    return result;
  }
}
