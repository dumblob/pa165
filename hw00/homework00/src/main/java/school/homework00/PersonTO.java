/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package school.homework00;

import java.io.Serializable;

/**
 * A specific transfer object
 * @author honza
 */
public class PersonTO implements Serializable {
  public Long id;
  public String name;
  public Long childrenCount;

  public PersonTO(Long id, String name, Long childrenCount) {
    this.id = id;
    this.name = name;
    this.childrenCount = childrenCount;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getChildrenCount() {
    return childrenCount;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setChildrenCount(Long childrenCount) {
    this.childrenCount = childrenCount;
  }
}