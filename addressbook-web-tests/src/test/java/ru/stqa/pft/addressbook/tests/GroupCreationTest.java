package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
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

public class GroupCreationTest extends TestBase {

  // Провайдер тестовых данных
  @DataProvider
  public Iterator<Object[]> validGroupsXml() throws IOException {   // Итератор массивов объектов
    // Создаем объект типа Reader для чтения тестовых данных из файла
    // Но сначала еще обернем в BufferedReader для получения нужного метода readLine чтения строк из файла
    // Примечание: try()играет роль автоматического закрытия файла (открытого на чтение FileReader или запись writer)
    try (BufferedReader reader =
                 new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GroupData.class); // обработка аннотации в классе GroupData

      // Явно преобразовали тип (приведение типа) потому что fromXML(xml) возвращает объект какого-то неясного типа
      List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);

      //К каждому объекту  groups.stream().map() применить ф-ю ((g) -> new Object[] {g})кот. этот объект завернет
      //  в массив кот. состоит из одного этого объекта. После применения анонимной ф-ии вызывается collect( кот.
      // из потока собирает обратно список Collectors.toList() и у получившегося списка берется iterator()
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsJson() throws IOException {   // Итератор массивов объектов
    // Создаем объект типа Reader для чтения тестовых данных из файла
    // Но сначала еще обернем в BufferedReader для получения нужного метода readLine чтения строк из файла
    // Примечание: try()играет роль автоматического закрытия файла (открытого на чтение FileReader или запись writer)
    try (BufferedReader reader =
                 new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType()); // Это типа List<GroupData> .class

      //К каждому объекту  groups.stream().map() применить ф-ю ((g) -> new Object[] {g})кот. этот объект завернет
      //  в массив кот. состоит из одного этого объекта. После применения анонимной ф-ии вызывается collect( кот.
      // из потока собирает обратно список Collectors.toList() и у получившегося списка берется iterator()
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }


  @Test(dataProvider = "validGroupsJson")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    // ХЭШИРОВАНИЕ по размеру групп , если падает то дальше тест не выполняется
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().all();

    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
// Проверка двух объектов по Hamcrest
//    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before));
    assertThat(after, equalTo(before.withAdded(group)));
  }

  // Негативный тест по неправильному имени  т.е с " ' "

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test'");
    app.group().create(group);
    // Группа не должна создаться
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }


}
