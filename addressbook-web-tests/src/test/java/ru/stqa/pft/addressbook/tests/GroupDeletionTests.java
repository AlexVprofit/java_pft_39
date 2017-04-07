package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

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
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    app.group().delete(index);
    List<GroupData> after = app.group().list();

    // Сравнение размеров списков
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    // Сравнение списков  до и после удаления
    Assert.assertEquals(before, after);

  }

}
