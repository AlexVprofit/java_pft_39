package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by LEN on 09.04.2017.
 */
public class ContactPhoneTests extends TestBase {

  @Test
  public void testContactPhones () {
    app.goTo().goHome();
    // Загружаем список (множество) контактов  и случайный контакт выделяем
    ContactData contact = app.contact().all().iterator().next();
    // загружаем информацию из формы редактирования контакта в таблицу контактов
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    //  Это и есть метод обратных проверок (т.е. от формы редактирования контакта contactInfoFromEditForm )
    assertThat(contact.getNew_adress(), equalTo(contactInfoFromEditForm.getNew_adress()));
    assertThat(contact.getEmail1(), equalTo(cleaned(contactInfoFromEditForm.getEmail1())));
    assertThat(contact.getEmail2(), equalTo(cleaned(contactInfoFromEditForm.getEmail2())));
    assertThat(contact.getEmail3(), equalTo(cleaned(contactInfoFromEditForm.getEmail3())));
    assertThat(contact.getTelHome(), equalTo(cleaned(contactInfoFromEditForm.getTelHome())));
    assertThat(contact.getMobilPhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilPhone())));
    assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
    assertThat(contact.getPhone2Phone(), equalTo(cleaned(contactInfoFromEditForm.getPhone2Phone())));

  }

  // Форматирование строки телефона (приведение к требуемому виду, посредством функции замены используя Regex)
public String cleaned(String phone) {
    return phone.replaceAll("\\s","").replaceAll("[-()]","");

}

}
