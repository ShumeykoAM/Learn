package com.services.restful;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kot
 * @ created 25.03.2017
 * @ $Author$
 * @ $Revision$
 */

//Наш старый добрый POJO у которого должен быть конструктор без параметров и геттеры и сеттеры
@XmlRootElement  //Это значит что объект данного класса может преобразовываться в xml или json, преобразованием занимается JEE сервер
@XmlAccessorType(XmlAccessType.PROPERTY)
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

    //@XmlAttribute
    @XmlElement(name = "name")
    private String name;
}
