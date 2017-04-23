
package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by LEN on 23.04.2017.
 */

public class ContactDataId extends ForwardingSet<ContactData> {
  private Set<ContactData> delegate;

  public ContactDataId(ContactData contactData) {
    this.delegate = new HashSet<ContactData>(contactData.delegate);
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }
}
