package cz.muni.fi.pa165.books;

import java.util.List;

/**
 * Interface for library.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public interface BookLibrary {

    List<Book> getAllBooks();

    void createBook(Book book);

    Book getBook(Long id);

    void updateBook(Book book);

    void deleteBook(Long id);

    List<Customer> getAllCustomers();

    void createCustomer(Customer customer);

    Customer getCustomer(Long id);

    void updateCustomer(Customer customer);

    void deleteCustomer(Long id);

    List<Borrow> getBorrows(Book book, boolean activeOnly);

}
