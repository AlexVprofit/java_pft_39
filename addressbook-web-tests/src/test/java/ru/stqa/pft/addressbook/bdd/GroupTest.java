package ru.stqa.pft.addressbook.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

// анотация указывающая где именно находятся файлы с описанием сценариев "pretty" - стиль отчета и куда его выводить
@CucumberOptions(features = "classpath:bdd", plugin = {"pretty", "html:build/cucumber-rebort"})
public class GroupTest extends AbstractTestNGCucumberTests { // класс запускатель
}
