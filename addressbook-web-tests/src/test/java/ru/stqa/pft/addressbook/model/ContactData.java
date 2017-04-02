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

  // Преобразование в строку (для получения текстового представления элементов вместо цифрового)
  @Override
  public String toString() {
    return "ContactData{" +
            "firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", title='" + title + '\'' +
            ", company='" + company + '\'' +
            ", new_adress='" + new_adress + '\'' +
            ", telhome='" + telhome + '\'' +
            ", group='" + group + '\'' +
            '}';
  }

  // Формирование правил сравнения объектов для контакта(свой метод описываем equals т.к. он ещё не был описан)
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (title != null ? !title.equals(that.title) : that.title != null) return false;
    if (company != null ? !company.equals(that.company) : that.company != null) return false;
    if (new_adress != null ? !new_adress.equals(that.new_adress) : that.new_adress != null) return false;
    if (telhome != null ? !telhome.equals(that.telhome) : that.telhome != null) return false;
    return group != null ? group.equals(that.group) : that.group == null;
  }

  @Override
  public int hashCode() {
    int result = firstname != null ? firstname.hashCode() : 0;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (company != null ? company.hashCode() : 0);
    result = 31 * result + (new_adress != null ? new_adress.hashCode() : 0);
    result = 31 * result + (telhome != null ? telhome.hashCode() : 0);
    result = 31 * result + (group != null ? group.hashCode() : 0);
    return result;
  }
}
