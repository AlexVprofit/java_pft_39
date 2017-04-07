package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by LEN on 19.03.2017.
 */
public class GroupModificationTests extends TestBase {

  // Инициализация локальная - Подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    // Предусловие Проверка наличия хоть одной группы
    // Если при проверке выясниться, что групп нет, то будет созданна группа
    app.group().check(new GroupData().withName("test1"));
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    GroupData group = new GroupData().withId(before.get(index).getId())
            .withName("test1").withHeader("test2 FOR VERIFICATION").withFooter("test3");
    app.group().modify(index, group);
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size());

    // Для логики/красоты удаляем предыдущий список и записываем модифицированный список
    before.remove(index);
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
