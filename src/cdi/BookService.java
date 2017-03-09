package cdi;

import javax.inject.Inject;

/**
 * @author sbt-shumeyko-am
 * @ created 09.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class BookService
{
    @Inject @ThirteenDigits
    private NumberGenerator numberGenerator;
    public Book createBook(String name)
    {
        Book book = new Book(name);
        book.setNumber(numberGenerator.generateNumber());
        return book;
    }
        
}
