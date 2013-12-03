package school.homework00;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Jan Pacner
 */
public class App {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestDBPU");

  public static void dump() {
    System.out.println("### DUMP ###");
    print(getPersons());
  }

  public static void printList(List<Object[]> l) {
    for (Object[] i : l) {
      for (Object j : i) {
        // Object has always toString() method
        System.out.print(j + "|");
      }
      System.out.println();
    }
  }

  public static void printListTO(List<PersonTO> l) {
    for (PersonTO p : l) {
      System.out.println(p.getId().toString() + "|" + p.getName() + "|" +
              p.getChildrenCount().toString());
    }
  }

  public static void print(List<Person> l) {
    for (Person p : l) {
      System.out.print(p.getFirstName() + "|");
      System.out.print(p.getLastName() + "|");
      System.out.print(p.getAddress() + "|");
      System.out.print(p.getGender() + "|");
      System.out.print(p.getBirthdate());
      System.out.println();
      if (p.getFather() != null) {
        System.out.print("    FATHER: " + p.getFather().getFirstName() + "|");
        System.out.println(p.getFather().getLastName());
      }
      if (! p.getTraits().isEmpty()) {
        System.out.print("    TRAITS: ");
        for (Trait x : p.getTraits()) System.out.print(x.getTrait() + "|");
        System.out.println();
      }
    }
  }

  public static void main(String[] args) throws ParseException {
    EntityManager em1 = emf.createEntityManager();

    Person p = new Person("Paul", "Smith", "Botanicka 68a, Brno", "male", new Date(50000), null);

    em1.getTransaction().begin();
    em1.persist(p);
    em1.getTransaction().commit();
    dump();

    em1.getTransaction().begin();
    p.setAddress("Chvalovice 12");
    p.setLastName("Brown");
    em1.getTransaction().commit();
    dump();

    Person pp = new Person("Paul", "Brown Jr.", "Botanicka 68a, Brno", "male",
            new Date(90000), p);
    em1.getTransaction().begin();
    em1.persist(pp);
    em1.getTransaction().commit();
    dump();

    Trait t = new Trait("vysoky");
    Trait tt = new Trait("mel zloutenku");
    p = new Person("PPP", "PPP last", "PPP addr", "female", new Date(80000), null);
    em1.getTransaction().begin();
    p.getTraits().add(t);
    p.getTraits().add(tt);
    em1.persist(p);
    em1.getTransaction().commit();
    dump();

    p = new Person("John", "Novacek", "Prague", "male",
            //new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(""), null);
            new SimpleDateFormat("dd.M.yyyy").parse("25.4.1968"), null);
    pp = new Person("Petr", "Novacek", "Prague", "male",
            new SimpleDateFormat("dd.M.yyyy").parse("25.4.1986"), p);
    Person ppp = new Person("Jane", "Novacek", "Prague", "female",
            new SimpleDateFormat("dd.M.yyyy").parse("25.4.1986"), pp);
    em1.getTransaction().begin();
    em1.persist(p);
    em1.persist(pp);
    em1.persist(ppp);
    em1.getTransaction().commit();
    dump();

    System.out.println("### getParents() ###");
    print(getParents());

    System.out.println("### getAllBornOn() ###");
    // should return Petr & Jan Novacek
    printList(getAllBornOn(new SimpleDateFormat("dd.M.yyyy").parse("25.4.1986")));

    System.out.println("### getPersons(\"Paul\") ###");
    printListTO(getPersons("Paul"));

    System.out.println("### getNonParents() ###");
    System.out.println(getNonParents());
  }

  public static List<Person> getPersons() {
    EntityManager em = emf.createEntityManager();
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Person> cq = cb.createQuery(Person.class);
    Root<Person> p = cq.from(Person.class);
    cq.select(p);
    TypedQuery<Person> q = em.createQuery(cq);
    //TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p", Person.class);
    return q.getResultList();
  }

  public static List<PersonTO> getPersons(String name) {
    EntityManager em = emf.createEntityManager();
    TypedQuery<PersonTO> q = em.createQuery(
            // was too lazy to concatenate first and last name...
            // => using only firstName as simplification
            "SELECT new school.homework00.PersonTO(p.id, p.firstName, COUNT(child)) " +
            "FROM Person p LEFT JOIN p.father child " +
            "WHERE p.firstName = :name " +
            "GROUP BY p.id, p.firstName",
            PersonTO.class);
    q.setParameter("name", name);
    // why the hell JPA doesn't support automatic parameter search (e.g.
    // using an annotation denoting the method from which the local
    // variables could be resolved)?
    return q.getResultList();
  }

  public static List<Person> getParents() {
    EntityManager em = emf.createEntityManager();
    TypedQuery<Person> q = em.createNamedQuery("getParents", Person.class);
    return q.getResultList();
  }

  public static Long getNonParents() {
    EntityManager em = emf.createEntityManager();
    TypedQuery<Long> q = em.createQuery(
            "SELECT COUNT(f) FROM Person f LEFT JOIN f.father x WHERE f.father IS NULL",
            Long.class);
    return q.getSingleResult();
  }

  public static List<Object[]> getAllBornOn(Date d) {
    EntityManager em = emf.createEntityManager();
    TypedQuery<Object[]> q = em.createQuery(
            "SELECT p.firstName, p.lastName FROM Person p WHERE p.birthdate = :d",
            Object[].class);
    return q.setParameter("d", d).getResultList();
  }
}