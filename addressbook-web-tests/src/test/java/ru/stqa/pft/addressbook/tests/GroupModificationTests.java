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
    Groups before = app.group().all();
    GroupData modifyGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifyGroup.getId())
            .withName("test1").withHeader("test2 FOR VERIFICATION").withFooter("test3");
    app.group().modify(group);
    // ХЭШИРОВАНИЕ по размеру групп , если падает то дальше тест не выполняется
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(modifyGroup).withAdded(group)));
  }

}
