/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.books;

import java.util.List;

/**
 *
 * @author honza
 */
public interface CustomerList {
    Customer getCustomer(Long id);

    void updateCustomer(Customer book);

    void deleteCustomer(Long id);

    List<Customer> getAllCustomers();

    void createCustomer(Customer c);

    //FIXME
    //List<Borrow> getBorrows(Customer book, boolean activeOnly);
}