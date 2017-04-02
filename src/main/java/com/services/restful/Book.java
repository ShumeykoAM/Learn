package com.services.restful;

import javax.xml.bind.annotation.*;

/**
 * @author kot
 * @ created 25.03.2017
 * @ $Author$
 * @ $Revision$
 */

//Наш старый добрый POJO у которого должен быть конструктор без параметров и геттеры и сеттеры
@XmlRootElement  //Это значит что объект данного класса может преобразовываться в xml или json, преобразованием занимается JEE сервер
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Book", propOrder = {"name"})
public class Book
{
    public Book()
    {
        this.name = "simple";
    }
    public Book(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @XmlElement
    private String name;
}
