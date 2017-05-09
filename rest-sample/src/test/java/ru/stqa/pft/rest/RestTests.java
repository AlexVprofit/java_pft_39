package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {

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
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent().asString(); // авторизация и получение списка всех багрепортов в формате json
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    // по ключу "issues" извлекаем нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    // преобразование в множ-во объектов типа
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");//имя и пароль пользователя т.е авторизация
  }

  private int createIssue(Issue newIssue) throws IOException { // создание багрепорта
    String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                      new BasicNameValuePair("description", newIssue.getDescription()))) // имя и значение параметра
            .returnContent().asString(); // авторизация и получение списка всех багрепортов в формате json
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    return parsed.getAsJsonObject().get("issue_id").getAsInt(); // идентификатор созданного багрепорта
  }

}
