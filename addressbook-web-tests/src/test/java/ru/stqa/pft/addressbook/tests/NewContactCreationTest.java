package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewContactCreationTest extends TestBase {

  // Провайдер тестовых данных
  @DataProvider
  public Iterator<Object[]> validContactsXml() throws IOException {   // итератор массивов объектов
    // Создаем объект типа Reader для чтения тестовых данных из файла
    // Но сначала еще обернем в BufferedReader для получения нужного метода readLine чтения строк из файла
    // Примечание: try()играет роль автоматического закрытия файла (открытого на чтение FileReader или запись writer)
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class); // обработка аннотации в классе ContactData

      // Явно преобразовали тип (приведение типа) потому что fromXML(xml) возвращает объект какого-то неясного типа
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);

      //К каждому объекту  ContactData.stream().map() применить ф-ю ((g) -> new Object[] {g})кот. этот объект завернет
      //  в массив кот. состоит из одного этого объекта. После применения анонимной ф-ии вызывается collect( кот.
      // из потока собирает обратно список Collectors.toList() и у получившегося списка берется iterator()
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsJson() throws IOException {   // итератор массивов объектов
    // Создаем объект типа Reader для чтения тестовых данных из файла
    // Но сначала еще обернем в BufferedReader для получения нужного метода readLine чтения строк из файла
    // Примечание: try()играет роль автоматического закрытия файла (открытого на чтение FileReader или запись writer)
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType()); // Это типа List<ContactData> .class

      //К каждому объекту contacts.stream().map() применить ф-ю ((g) -> new Object[] {g})кот. этот объект завернет
      //  в массив кот. состоит из одного этого объекта. После применения анонимной ф-ии вызывается collect( кот.
      // из потока собирает обратно список Collectors.toList() и у получившегося списка берется iterator()
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }


  //  @Test(enabled = false)
  @Test(dataProvider = "validContactsJson")
  public void testNewContactCreation(ContactData contact) {
//    ContactData contact = new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
//            .withCompany("Education").withNew_adress("new adress").withTelHome("12345").withGroup("test1");

    Groups groups = app.db().groups(); // извлекли инфу из бд о всех группах, что бы выбрать каккую-то одну из них
    contact.inGroup(groups.iterator().next()); // помещение контакта в какую-нибудь группу

    app.goTo().goHome();
    Contacts before = app.db().contacts();
    app.contact().gotoAddNew();
    app.contact().create(contact, true);
    // хэширование по размеру групп , если падает то дальше тест не выполняется
    assertThat(app.contact().сount(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    ContactData contactnew =  app.db().contactsid(contact.getId());

    // Превращаем список after в поток объектов типа ContactData (а фактически в поток идентификаторов)
    // Написана анонимная фун-я кот. в качестве параметров принимает g , а в качестве результата выдает идентификатор
    // т.е. преобразовали в число  (и получили из объектов типа группа g поток целых чисел) и получили МАХ идентификар
    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    assertThat(after, equalTo(
            before.withAdded(contactnew)));
    // проверка контактов считанных с UI с контактами считаными по запросу с бд
    verifyContactListInUI();
  }

  //  @Test(enabled = false)
  @Test(enabled = false)
  public void testBadNewContactCreation() {
    app.goTo().goHome();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Alex1 '").withLastname("Alexbond").withTitle("Title")
            .withCompany("Education").withNew_adress("new adress").withTelHome("12345")/*.withGroup("test1")*/;
    app.contact().gotoAddNew();
    app.contact().create(contact, true);
    // Группа не должна создаться
    assertThat(app.contact().сount(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}