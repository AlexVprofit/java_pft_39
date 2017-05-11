package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException { // получение Commits из Github (вывведем на консоль историю какого-либо репозитория)
    // установка соединения через удаленный прог-й интерфейс
    Github github = new RtGithub("ebc53d7aa9bfcb74b3997fec8a3c85b424f7698f"); // token авторизации
    // читаем историю проекта
    RepoCommits commits = github.repos().get(new Coordinates.Simple("AlexVprofit", "java_pft_39")).commits();
    // итерация по commits с пустым набором пар (new ImmutableMap.Builder<String, String>().build())
    for (RepoCommit commit: commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(commit); // ссылки
      System.out.println(new RepoCommit.Smart(commit).message() ); // более детальная инфор-я т.е комментарии
    }
  }
}
