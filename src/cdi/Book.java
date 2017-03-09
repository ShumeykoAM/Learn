package cdi;

/**
 * @author sbt-shumeyko-am
 * @ created 09.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Book
{
    public Book(String name)
    {
        this.name = name;
    }
    public int getNumber()
    {
        return number;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public String getName()
    {
    
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String name;
    public int number;
}
