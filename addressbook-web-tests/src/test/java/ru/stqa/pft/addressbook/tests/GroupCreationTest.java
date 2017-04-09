package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test2");
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
