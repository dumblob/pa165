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
 * @author Martin Kuba makub@ics.muni.cz
 */
/*
 * reaguje to na url zacinajucu na books, v url moze byt este id knihy event je metoda
 */
@UrlBinding("/books/{$event}/{book.id}")
public class BooksActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(BooksActionBean.class);

    @SpringBean //Spring can inject even to private and protected fields
    protected BookLibrary bookLibrary;

    //--- part for showing a list of books ----
    private List<Book> books;

    /*
     * zobrazenie zaciatocneho zoznamu
     * defaulthandler ked nie je za url books/ nic
     */
    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        books = bookLibrary.getAllBooks();
        return new ForwardResolution("/book/list.jsp");
    }

    public List<Book> getBooks() {
        return books;
    }

    //--- part for adding a book ----

    /*
     * Kontroluje polia ktore sa plnia
     * ak nie su splnene rovno sa spusti .jsp stranka
     */
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "author", required = true),
            @Validate(on = {"add", "save"}, field = "title", required = true),
            @Validate(on = {"add", "save"}, field = "year", required = true, minvalue = 800)
    })
    private Book book;

    public Resolution add() {
        //premenna book uz bude naplnena udajmi z formularu
        log.debug("add() book={}", book);
        bookLibrary.createBook(book);
        getContext().getMessages().add(new LocalizableMessage("book.add.message",escapeHTML(book.getTitle()),escapeHTML(book.getAuthor())));
        //redirect aby uzivatel mohol klikat na reload
        return new RedirectResolution(this.getClass(), "list");
    }

    /*
     * tato metoda sa zavola ak zadal uzivatel zle data vo folmulari
     * 
     * ja si len zase naplnim knihy aby som ich mohol zobrazit
     */
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        books = bookLibrary.getAllBooks();
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

    /*
     * Editacia v 2krokoch,
     * prva vytiahne knihu z DB 
     * 
     * tato metoda sa zavola pred metodou edit, alebo save
     */
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
        log.debug("cancel() book={}", book);
        //bookLibrary.updateBook(book);
        return new RedirectResolution(this.getClass(), "list");
    }
}
