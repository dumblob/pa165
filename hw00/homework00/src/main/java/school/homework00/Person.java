/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package school.homework00;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author Jan Pacner
 */
@Entity
@Table(name = "SimplePerson")
@NamedQueries({
  @NamedQuery(name="getParents", query=
        "SELECT f FROM Person f RIGHT JOIN f.father x WHERE f.father IS NOT NULL")
        //"SELECT f FROM Person f WHERE f.id IN (SELECT p.id FROM Person p WHERE p.father IS NOT NULL GROUP BY p.id)")
})
public class Person implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  //@GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(name = "givenName")
  private String firstName;
  @Column(length = 50)
  private String lastName;
  private String address;
  @Column(nullable = false, length = 6)
  private String gender;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date birthdate;
  @ManyToOne
  private Person father;
  @OneToMany(mappedBy = "p", cascade = CascadeType.ALL)
  private List<Trait> traits;

  public Person(String firstName, String lastName, String address,
          String gender, Date birthdate, Person father) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.gender = gender;
    this.birthdate = birthdate;
    this.father = father;
    this.traits = new ArrayList<Trait>();
  }

  public Person() {
  }

  //@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  //private List<Skill> skills = new ArrayList<Skill>();
  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getGender() {
    return gender;
  }

  public Date getBirthdate() {
    return birthdate;
  }
  public Person getFather() {
    return father;
  }

  public List<Trait> getTraits() {
    return traits;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setGender(String g) {
    this.gender = g;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public void setFather(Person father) {
    this.father = father;
  }

  public void setTraits(List<Trait> traits) {
    this.traits = traits;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Person other = (Person) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }
}
