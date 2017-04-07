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

  // Инициализация - Подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    // Предусловие Проверка наличия хоть одной группы
    // Если при проверке выясниться, что групп нет, то будет созданна группа
    app.group().check(new GroupData().withName("test1"));
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.group().all();
    // Извлекаем элемент из множества
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    Groups after = app.group().all();

    // Сравнение размеров списков
    assertEquals(after.size(), before.size() - 1);

    // Сравнение списков  до и после удаления
    assertThat(after, equalTo(before.without(deletedGroup)));
  }

}
