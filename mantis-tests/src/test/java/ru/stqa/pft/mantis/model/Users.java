package ru.stqa.pft.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Users extends ForwardingSet<UserData> {

  private Set<UserData> delegate;

  public Users(Users users) {
    // Берем множество из параметра (Users users) строим новое множество содержащее теже элементы
    // и присваиваем его в качестве атрибута в новый создаваемый конструктором (this.delegete) объект
    this.delegate = new HashSet<UserData>(users.delegate);
  }

  public Users() {
    this.delegate = new HashSet<UserData>();
  }

  // конструктор кот. по произвольной коллекции строит объект типа UserData
  public Users(Collection<UserData> users) {
    this.delegate = new HashSet<UserData>(users); // строим новый HashSet из коллекции
  }

  // Метод delegete Вернет этот объект delegete
  @Override
  protected Set<UserData> delegate() {
    return delegate;
  }

  public Users withAdded(UserData user)
  {
    Users users=new Users (this);
    users.add(user);
    return users;
  }

  public Users without(UserData user)
  {
    Users users=new Users (this);
    users.remove(user);
    return users;
  }

}
