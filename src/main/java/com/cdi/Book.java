package com.cdi;

/**
 * @author kot
 * @ created 11.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Book
{
    public Book(String name) { this.name = name; }
    public Book() { this(""); }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    private int number = 0;
    private String name;
}
