package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewContactCreationTest extends TestBase {

  //  @Test(enabled = false)
  @Test
  public void testNewContactCreation() {
    app.goTo().goHome();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
            .withCompany("Education").withNew_adress("new adress").withTelHome("12345").withGroup("test1");
    app.contact().gotoAddNew();
    app.contact().create(contact, true);
    // ХЭШИРОВАНИЕ по размеру групп , если падает то дальше тест не выполняется
    assertThat(app.contact().сount(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();

    // Превращаем список after в поток объектов типа ContactData (а фактически в поток идентификаторов)
    // Написана анонимная фун-я кот. в качестве параметров принимает g , а в качестве результата выдает идентификатор
    // т.е. преобразовали в число  (и получили из объектов типа группа g поток целых чисел) и получили МАХ идентификар
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  //  @Test(enabled = false)
  @Test
  public void testBadNewContactCreation() {
    app.goTo().goHome();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Alex1 '").withLastname("Alexbond").withTitle("Title")
            .withCompany("Education").withNew_adress("new adress").withTelHome("12345").withGroup("test1");
    app.contact().gotoAddNew();
    app.contact().create(contact, true);
    // Группа не должна создаться
    assertThat(app.contact().сount(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}
