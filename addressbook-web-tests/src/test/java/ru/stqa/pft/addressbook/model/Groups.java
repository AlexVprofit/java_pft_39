package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by LEN on 07.04.2017.
 */
public class Groups extends ForwardingSet<GroupData> {

  // метод delegete
  private Set<GroupData> delegete;

  public Groups(Groups groups) {
    // Берем множество из параметра (Groups groups) строим новое множество содержащее теже элементы
    // и присваиваем его в качестве атрибута в новый создаваемый конструктором (this.delegete) объект
    this.delegete = new HashSet<GroupData>(groups.delegete);
  }

  public Groups() {
    this.delegete = new HashSet<GroupData>();
  }

  // конструктор кот. по произвольной коллекции строит объект типа groups
  public Groups(Collection<GroupData> groups) {
    this.delegete = new HashSet<GroupData>(groups); // строим новый HashSet из коллекции
  }

  // Метод delegete Вернет этот объект delegete
  @Override
  protected Set<GroupData> delegate() {
    return delegete;
  }
  // Наши методы (Для цепочек и каскадов)
  public  Groups withAdded(GroupData group) {
    Groups groups = new Groups(this);
    // выше создали копию и внее добавляем объект из параметра(GroupData group)
    groups.add(group);
    return groups;
  }
//  А здесь делается копия из которой удалена какая-то группа
  public  Groups without(GroupData group) {
    Groups groups = new Groups(this);
    // выше создали копию и внее добавляем объект из параметра(GroupData group)
    groups.remove(group);
    return groups;
  }

}
