package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  // инициализация - подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      // Предусловие Проверка наличия хоть одной группы
      // Если при проверке выясниться, что групп нет, то будет созданна группа
      app.group().check(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.db().groups();
    // извлекаем элемент из множества
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    // хэширование по размеру групп (Сравнение размеров списков) , если падает то дальше тест не выполняется
    assertThat(app.group().count(), equalTo(before.size() - 1));
    Groups after = app.db().groups();

    // сравнение списков  до и после удаления
    assertThat(after, equalTo(before.without(deletedGroup)));
    // проверка UI
    verifyGroupListInUI();
  }

}
