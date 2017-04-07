package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id = Integer.MAX_VALUE;
  private String firstname;
  private String lastname;
  private String title;
  private String company;
  private String new_adress;
  private String telhome;
  private String group;

  public int getId() {
    return id;
  }

  public ContactData withId(int id) {
    this.id = id;
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

  public ContactData withTelhome(String telhome) {
    this.telhome = telhome;
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
