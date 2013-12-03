package cz.muni.fi.pa165.books;

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
 * @author Jan Pacner
 */
@UrlBinding("/books/{$event}/{book.id}")
public class CustomersActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(CustomersActionBean.class);

    @SpringBean //Spring can inject even to private and protected fields
    protected CustomerList bookLibrary;

    //--- part for showing a list of books ----
    private List<Book> books;

    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        books = bookLibrary.getAllCustomers();
        return new ForwardResolution("/book/list.jsp");
    }

    public List<Book> getCustomers() {
        return books;
    }

    //--- part for adding a book ----

    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "author", required = true),
            @Validate(on = {"add", "save"}, field = "title", required = true),
            @Validate(on = {"add", "save"}, field = "year", required = true, minvalue = 800)
    })
    private Book book;

    public Resolution add() {
        log.debug("add() book={}", book);
        bookLibrary.createBook(book);
        getContext().getMessages().add(new LocalizableMessage("book.add.message",escapeHTML(book.getTitle()),escapeHTML(book.getAuthor())));
        return new RedirectResolution(this.getClass(), "list");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        books = bookLibrary.getAllCustomers();
        //return null to let the event handling continue
        return null;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    //--- part for deleting a book ----

    public Resolution delete() {
        log.debug("delete({})", book.getId());
        //only id is filled by the form
        book = bookLibrary.getBook(book.getId());
        bookLibrary.deleteBook(book.getId());
        getContext().getMessages().add(new LocalizableMessage("book.delete.message",escapeHTML(book.getTitle()),escapeHTML(book.getAuthor())));
        return new RedirectResolution(this.getClass(), "list");
    }

    //--- part for editing a book ----

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadBookFromDatabase() {
        String ids = getContext().getRequest().getParameter("book.id");
        if (ids == null) return;
        book = bookLibrary.getBook(Long.parseLong(ids));
    }

    public Resolution edit() {
        log.debug("edit() book={}", book);
        return new ForwardResolution("/book/edit.jsp");
    }

    public Resolution save() {
        log.debug("save() book={}", book);
        bookLibrary.updateBook(book);
        return new RedirectResolution(this.getClass(), "list");
    }
    public Resolution cancel() {
        log.debug("cancel() book={}");
        return new RedirectResolution(this.getClass(), "list");
    }
}