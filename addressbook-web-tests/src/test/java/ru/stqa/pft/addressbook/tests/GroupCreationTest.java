package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

  // Провайдер тестовых данных
  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {   // Итератор массивов объектов
    List<Object[]> list = new ArrayList<Object[]>();
    /* Это уже не нужно
    list.add(new Object[] {new GroupData().withName("test1").withHeader("header 1").withFooter("footer 1")});
    list.add(new Object[] {new GroupData().withName("test2").withHeader("header 2").withFooter("footer 2")});
    list.add(new Object[] {new GroupData().withName("test3").withHeader("header 3").withFooter("footer 3")});
    */
    // Создаем объект типа Reader для чтения тестовых данных из файла
    // Но сначала еще обернем в BufferedReader для получения нужного метода readLine чтения строк из файла
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
    String line =reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      //Создается массив типа new Object[] из одного элемента и заполняем им массив list
      // (т.к. параметр метода testGroupCreation имеет тип GroupData group
      list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
      line =reader.readLine();
    }
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
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
