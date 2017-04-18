package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailTests extends TestBase {

  // инициализация локальная - подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    // На главную страницу контактов
    app.goTo().goHome();

  }

  @Test
  public void testContactDetails() {
    // Загружаем список (множество) контактов  и случайный контакт выделяем
    ContactData contactmain = app.contact().all().iterator().next();
    // Загружаем данные со страницы с подробной информацией о контакте
    ContactData contact = app.contact().allDetails(contactmain, 6);

    // загружаем информацию из формы редактирования контакта в таблицу контактов
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contactmain, 1);

    //  Проверка (метод обратных проверок (т.е. от формы редактирования контакта contactInfoFromEditForm ))
    assertThat(cleaned(contact.getAllDetails()), equalTo(mergeDetailes(contactInfoFromEditForm)));
  }

  private String mergeDetailes(ContactData contact) {
    // Создаем список - Превращаем в поток - (выкидываем элементы равные "" filter с пареметрами: анонимная ф-я
    // которая на вход принимает строку и возвращает True или False ) получили поток где нет пустых строк, а фун-й мар()
    //  применяем ко всем эл-м потока ф-ю ( cleaned() по Регулярным выражениям )  и возвращаем обработанный поток
    // и склеиваем их вместе collect(Collectors.joining("\n"))
    return Arrays.asList(contact.getFirstname(), contact.getLastname(), contact.getNew_adress(),
            contact.getTelHome(), contact.getMobilPhone(), contact.getWorkPhone())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactDetailTests::cleaned)
            .collect(Collectors.joining(""));
  }

  // Форматирование строки (приведение к требуемому виду, посредством функции замены используя Regex)
  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("H:", "").replaceAll("M:", "").replaceAll("W:", "")
            .replaceAll("[-()]", "");
  }


}
