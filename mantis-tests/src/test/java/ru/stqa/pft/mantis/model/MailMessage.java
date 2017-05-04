package ru.stqa.pft.mantis.model;

public class MailMessage {

  public String to; // поле кому пришло письмо

  public String text;  // поле текст письма

  public MailMessage (String to, String text)
  {
    this.to=to;
    this.text=text;
  }
}

