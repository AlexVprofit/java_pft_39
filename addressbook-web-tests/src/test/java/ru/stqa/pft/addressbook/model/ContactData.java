package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id = Integer.MAX_VALUE;
  private String firstname;
  private String lastname;
  private String title;
  private String company;
  private String new_adress;
  private String telhome;
  private String mobilPhone;
  private String workPhone;
  private String phone2Phone;
  private String allEmails;
  private String allPhones;
  private String allDetails;
  private String email1;
  private String email2;
  private String email3;
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
            ", telhome='" + telhome + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (new_adress != null ? !new_adress.equals(that.new_adress) : that.new_adress != null) return false;
    return telhome != null ? telhome.equals(that.telhome) : that.telhome == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (new_adress != null ? new_adress.hashCode() : 0);
    result = 31 * result + (telhome != null ? telhome.hashCode() : 0);
    return result;
  }
}
