package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstname;
  private final String lastname;
  private final String title;
  private final String company;
  private final String new_adress;
  private final String telhome;
  private String group;

  public ContactData(String firstname, String lastname, String title, String company, String new_adress,
                     String telhome, String group) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.title = title;
    this.company = company;
    this.new_adress = new_adress;
    this.telhome = telhome;
    this.group = group;
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

  public String getTelhome() {
    return telhome;
  }

  public String getGroup() {
    return group;
  }
}
