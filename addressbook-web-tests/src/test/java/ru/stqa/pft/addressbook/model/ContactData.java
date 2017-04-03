package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id;
  private final String firstname;
  private final String lastname;
  private final String title;
  private final String company;
  private final String new_adress;
  private final String telhome;
  private String group;

  public ContactData(String firstname, String lastname, String title, String company, String new_adress,
                     String telhome, String group) {

    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.lastname = lastname;
    this.title = title;
    this.company = company;
    this.new_adress = new_adress;
    this.telhome = telhome;
    this.group = group;
  }

  public ContactData(int id, String firstname, String lastname, String title, String company, String new_adress,
                     String telhome, String group) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.title = title;
    this.company = company;
    this.new_adress = new_adress;
    this.telhome = telhome;
    this.group = group;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (new_adress != null ? !new_adress.equals(that.new_adress) : that.new_adress != null) return false;
    return telhome != null ? telhome.equals(that.telhome) : that.telhome == null;
  }

  @Override
  public int hashCode() {
    int result = firstname != null ? firstname.hashCode() : 0;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (new_adress != null ? new_adress.hashCode() : 0);
    result = 31 * result + (telhome != null ? telhome.hashCode() : 0);
    return result;
  }
}
