package cz.muni.fi.pa165.books;

import java.util.*;

/**
 * Memory implementation.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class BookLibraryImpl implements BookLibrary {

    long bookCounter = 1;
    List<Book> books;
    long customerCounter = 100;
    List<Customer> customers;
    long borrowCounter = 1000;
    List<Borrow> borrows;

    public BookLibraryImpl() {
        books = Collections.synchronizedList(new ArrayList<Book>());
        Book b1 = new Book();
        b1.setAuthor("Božena Němcová");
        b1.setTitle("Babička");
        b1.setPaperback(false);
        b1.setYear(1855);
        b1.setColor(Book.Color.ORANGE);
        createBook(b1);
        Book b2 = new Book();
        b2.setAuthor("Karel May");
        b2.setTitle("Vinnetou");
        b2.setPaperback(true);
        b2.setYear(1893);
        b2.setColor(Book.Color.GREEN);
        createBook(b2);

        customers = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setFirstName("Jan");
        c1.setLastName("Novák");
        c1.setAddress("Severní 13");
        c1.setPhone("+420123456789");
        createCustomer(c1);
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public void createBook(Book book) {
        book.setId(bookCounter++);
        books.add(book);
    }

    @Override
    public Book getBook(Long id) {
        for(Book book : books) {
            if(id.equals(book.getId())) return (Book) book.clone();
        }
        return null;
    }

    @Override
    public void updateBook(Book book) {
        for (ListIterator<Book> iterator = books.listIterator(); iterator.hasNext(); ) {
            Book book2 = iterator.next();
            if (book2.getId().equals(book.getId())) {
                iterator.set(book);
                return;
            }
        }
    }

    @Override
    public void deleteBook(Long id) {
        for (Iterator<Book> iterator = books.listIterator(); iterator.hasNext(); ) {
            Book book = iterator.next();
            if (id.equals(book.getId())) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public void deleteCustomer(Long id) {
        for (ListIterator<Customer> iterator = customers.listIterator(); iterator.hasNext(); ) {
            Customer customer2 = iterator.next();
            if (id.equals(customer2.getId())) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void createCustomer(Customer customer) {
        customer.setId(customerCounter++);
        customers.add(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        for(Customer customer : customers) {
            if(id.equals(customer.getId())) return (Customer) customer.clone();
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        for (ListIterator<Customer> iterator = customers.listIterator(); iterator.hasNext(); ) {
            Customer customer2 = iterator.next();
            if (customer.getId().equals(customer2.getId())) {
                iterator.set(customer);
                return;
            }
        }
    }

    @Override
    public List<Borrow> getBorrows(Book book, boolean activeOnly) {
        return borrows;
    }
}
