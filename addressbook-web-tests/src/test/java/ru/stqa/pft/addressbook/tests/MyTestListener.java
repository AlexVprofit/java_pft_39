package ru.stqa.pft.addressbook.tests;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.stqa.pft.addressbook.appmeneger.ApplicationMeneger;
import ru.yandex.qatools.allure.annotations.Attachment;


public class MyTestListener implements ITestListener { // метод получения отчета с экрана в allure
  @Override
  public void onTestStart(ITestResult result) { // перед началом теста вызывается

  }

  @Override
  public void onTestSuccess(ITestResult result) { // теста вызывается тест успешен
    // снимаем скриншот c помощью способа контекста выполнения тестов т.е. вызваем метод находящийся в ApplicationMeneger
    ApplicationMeneger app = (ApplicationMeneger) result.getTestContext().getAttribute("app");
    saveScreenshot(app.takeScreeshot()); //метод получения скриншота,а метод saveScreenshot прикрепляет его к отчету Allure
  }

  @Override
  public void onTestFailure(ITestResult result) { // теста вызывается тест упал
  // снимаем скриншот c помощью способа контекста выполнения тестов т.е. вызваем метод находящийся в ApplicationMeneger
    ApplicationMeneger app = (ApplicationMeneger) result.getTestContext().getAttribute("app");
    saveScreenshot(app.takeScreeshot()); //метод получения скриншота,а метод saveScreenshot прикрепляет его к отчету Allure
  }

  // метод saveScreenshot прикрепляет скриншот к отчету Allure используя функции javaagent при объявлении @Attachment
  @Attachment(value = "Page screenshot", type = "image/png")
  public byte[] saveScreenshot(byte[] screenShot) {
    return screenShot;
  }

  @Override
  public void onTestSkipped(ITestResult result) { // теста вызывается тест пропущен -?

  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

  }

  @Override
  public void onStart(ITestContext context) {

  }

  @Override
  public void onFinish(ITestContext context) {

  }
}
