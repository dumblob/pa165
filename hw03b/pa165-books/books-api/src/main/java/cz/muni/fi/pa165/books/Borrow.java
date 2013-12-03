package cz.muni.fi.pa165.books;

import java.util.Calendar;

/**
 * Borrow entity.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class Borrow {

    private Long id;
    private Book book;
    private Customer customer;
    private Calendar borrowed;
    private Calendar expectedEnd;
    private Calendar returned;

    public Borrow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Calendar getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Calendar borrowed) {
        this.borrowed = borrowed;
    }

    public Calendar getExpectedEnd() {
        return expectedEnd;
    }

    public void setExpectedEnd(Calendar expectedEnd) {
        this.expectedEnd = expectedEnd;
    }

    public Calendar getReturned() {
        return returned;
    }

    public void setReturned(Calendar returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", book=" + book +
                ", customer=" + customer +
                ", borrowed=" + borrowed +
                ", expectedEnd=" + expectedEnd +
                ", returned=" + returned +
                '}';
    }
}
