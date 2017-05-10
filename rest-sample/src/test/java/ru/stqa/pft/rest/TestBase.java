package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Set;


public class TestBase {

//  protected static final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);
  protected  static final ApplicationManager app
        = new ApplicationManager();

  @BeforeSuite  // инициализация метода
  public void setUp() throws Exception {
    app.init();
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public  boolean isIssueOpen(int issueId) throws IOException {
    Set<Issue> oldIssues = getIssueStatus(issueId);
    String status = oldIssues.iterator().next().getState_name();
    String message = "Status bug: "+issueId+" is "+status;

    if (status.equals("Resolved")) {
      System.out.println(message);
      return false;
    }
    else  {
      System.out.println(message); // пропускаем тест
      return true;
    }
  }

  private Set<Issue> getIssueStatus(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get(("http://demo.bugify.com/api/issues/"+issueId+".json")))
            .returnContent().asString(); // авторизация и получение списка всех багрепортов в формате json
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    // по ключу "issues" извлекаем нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    // преобразование в множ-во объектов типа
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  public Executor getExecutor() {
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", ""); //имя и пароль пользователя т.е авторизация
  }

  public Set<Issue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent().asString(); // авторизация и получение списка всех багрепортов в формате json
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    // по ключу "issues" извлекаем нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    // преобразование в множ-во объектов типа
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  public int createIssue(Issue newIssue) throws IOException { // создание багрепорта
    String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription()))) // имя и значение параметра
            .returnContent().asString(); // авторизация и получение списка всех багрепортов в формате json
    JsonElement parsed = new JsonParser().parse(json); // получили элемент типа JsonElement т.е проанализировали строку исп-я JsonParser()
    return parsed.getAsJsonObject().get("issue_id").getAsInt(); // идентификатор созданного багрепорта
  }


}
