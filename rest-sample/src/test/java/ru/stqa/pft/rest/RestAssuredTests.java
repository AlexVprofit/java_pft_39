package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

  @BeforeMethod
  public void init() { //имя и пароль пользователя т.е авторизация
    RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
  }

  @Test
  public void testCreateIssue () throws IOException { //создание новогобаг-репрота в багтрекере Bugify
    Set<Issue> oldIssues = getIssues(); // получаем множество объектов типа Issue
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");// новый объект
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues(); // получаем множество объектов типа Issue после добавления
    oldIssues.add(newIssue.withId(issueId));// к старому множ-ву добавл. объект
    assertEquals(newIssues, oldIssues);// сравниваем

  }

  private Set<Issue> getIssues() throws IOException {
   // авторизация и получение списка всех багрепортов в формате json
    String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    // по ключу "issues" извлекаем нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    // преобразование в множ-во объектов типа
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  private int createIssue(Issue newIssue) throws IOException { // создание багрепорта
    // авторизация и получение списка всех багрепортов в формате json
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject()) // имя и значение параметра
            .parameter("description", newIssue.getDescription()) // имя и значение параметра
    .post("http://demo.bugify.com/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    return parsed.getAsJsonObject().get("issue_id").getAsInt(); // идентификатор созданного багрепорта
  }

}
