package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by LEN on 09.04.2017.
 */
public class ContactPhoneTests extends TestBase {

  @Test
  public void testContactPhones() {
    app.goTo().goHome();
    // Загружаем список (множество) контактов  и случайный контакт выделяем
    ContactData contact = app.contact().all().iterator().next();
    // загружаем информацию из формы редактирования контакта в таблицу контактов
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact, 0);

    //  Метод обратных проверок (т.е. от формы редактирования контакта contactInfoFromEditForm )
    assertThat(cleanedAe(contact.getNew_adress()), equalTo(cleanedAe(contactInfoFromEditForm.getNew_adress())));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s -> !s.equals("")))
            .map(ContactPhoneTests::cleanedAe)
            .collect(Collectors.joining("\n"));
  }

  private String mergePhones(ContactData contact) {
    // Создаем список - Превращаем в поток - (выкидываем элементы равные "" filter с пареметрами: анонимная ф-я
    // которая на вход принимает строку и возвращает True или False ) получили поток где нет пустых строк, а фун-й мар()
    //  применяем ко всем эл-м потока ф-ю ( cleaned() по Регулярным выражениям )  и возвращаем обработанный поток
    // и склеиваем их вместе collect(Collectors.joining("\n"))
    return Arrays.asList(contact.getTelHome(), contact.getMobilPhone(), contact.getWorkPhone(), contact.getPhone2Phone())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  // Форматирование строки Адреса и Email (приведение к требуемому виду, посредством функции замены используя Regex)
  public static String cleanedAe(String address) {
    return address.replaceAll("\\s", "");
  }

  // Форматирование строки телефона (приведение к требуемому виду, посредством функции замены используя Regex)
  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

}
