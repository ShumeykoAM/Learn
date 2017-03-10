package com.cdi;

import com.WeldJUnit4Runner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * @author kot
 * @ created 12.03.2017
 * @ $Author$
 * @ $Revision$
 */

@RunWith(WeldJUnit4Runner.class)
public class TestNumberGenerator
{
    //Здесть указываем что нужно внедрить конкретную реализацию, в данном случае этим займется Weld контейнер
    @Inject
    private BookService bookService;

    @Test
    public void testEntry()
    {
        Book book = bookService.createBook("H2SO4");

        int n = book.getNumber();

        Assert.assertEquals(0, n);
        
    }


}
