/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package school.homework00;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jan Pacner
 */
@Entity
public class Trait implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private Person p;
  private String trait;

  public Trait() {
  }

  public Trait(String trait) {
    this.trait = trait;
  }

  public Long getId() {
    return id;
  }

  public Person getP() {
    return p;
  }

  public String getTrait() {
    return trait;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setP(Person p) {
    this.p = p;
  }

  public void setTrait(String trait) {
    this.trait = trait;
  }
}
