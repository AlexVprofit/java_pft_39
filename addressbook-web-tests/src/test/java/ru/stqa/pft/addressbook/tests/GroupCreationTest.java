package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTest extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test2", null, null);
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(group);
    // Сортировка спискков ( до и после создания )
    // Лямбда выражение: анонимная функция -> на входе два параметра, это две группы, а сравниваем 2 идентификатора
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    // Преобразование списков в множества (что бы сравнивать не задумываясь о порядке средования строк при сравнении)
    /*Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));*/

    // Теперь в преобразовании нет необходимости
    Assert.assertEquals(before, after);
  }

}
