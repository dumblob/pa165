/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.books;

import static cz.muni.fi.pa165.books.BooksActionBean.log;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Stripes ActionBean for handling book operations.
 *
 * @author xloffay
 */
@UrlBinding("/customers/{$event}/{customer.id}")
public class CustomersActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(BooksActionBean.class);

    @SpringBean //Spring can inject even to private and protected fields
    protected BookLibrary bookLibrary;

    //--- part for showing a list of books ----
    private List<Customer> customers;

    @DefaultHandler
    public Resolution list() {
        log.debug("Customers list()");
        customers = bookLibrary.getAllCustomers();
        return new ForwardResolution("/customer/list.jsp");
    }

    public List<Customer> getCustomers() {
        log.debug("List getCustomers()");
        return customers;
    }

    //--- part for adding a book ----

    /*
     * Kontroluje polia ktore sa plnia
     * ak nie su splnene rovno sa spusti .jsp stranka
     */
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "firstName", required = true),
            @Validate(on = {"add", "save"}, field = "lastName", required = true),
            @Validate(on = {"add", "save"}, field = "address", required = true),
            @Validate(on = {"add", "save"}, field = "phone", required = true)
    })
    private Customer customer;

    public Resolution add() {
        log.debug("add() customer={}", customer);
        bookLibrary.createCustomer(customer);
        getContext().getMessages().add(new LocalizableMessage("customer.add.message",escapeHTML(customer.getFirstName()),escapeHTML(customer.getLastName())));
        return new RedirectResolution(this.getClass(), "list");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        log.debug("handleValidationErrors");
        //fill up the data for the table if validation errors occured
        customers = bookLibrary.getAllCustomers();
        //return null to let the event handling continue
        return null;
    }

    public Customer getCustomer() {
        log.debug("getCustomer()");
        return customer;
    }

    public void setCustomer(Customer customer) {
        log.debug("setCustomer()");
        this.customer = customer;
    }

    //--- part for deleting a book ----
    //@HandlesEvent("edit")
    public Resolution delete() {
        log.debug("Customer delete({})", customer.getId());
        //only id is filled by the form
        customer = bookLibrary.getCustomer(customer.getId());
        bookLibrary.deleteCustomer(customer.getId());
        getContext().getMessages().add(new LocalizableMessage("customer.delete.message",escapeHTML(customer.getFirstName()),escapeHTML(customer.getLastName())));
        return new RedirectResolution(this.getClass(), "list");
    }

    //--- part for editing a book ----

    /*
     * Editacia v 2krokoch,
     * prva vytiahne knihu z DB 
     */
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadCustomerFromDatabase() {
        log.debug("@Before <edit><save>", customer);
        String ids = getContext().getRequest().getParameter("customer.id");
        if (ids == null) {
            log.debug("@Before return", customer);
            return;
        }
        customer = bookLibrary.getCustomer(Long.parseLong(ids));
    }

    public Resolution edit() {
        log.debug("edit() customer={}", customer);
        return new ForwardResolution("/customer/edit.jsp");
    }

    public Resolution save() {
        log.debug("save() customer={}", customer);
        bookLibrary.updateCustomer(customer);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution cancel() {
        log.debug("cancel() customer={}", customer);
        //bookLibrary.updateBook(book);
        return new RedirectResolution(this.getClass(), "list");
    }
}