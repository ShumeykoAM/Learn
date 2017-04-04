package com.services.restful;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.*;

/**
 * @author kot
 * @ created 26.03.2017
 * @ $Author$
 * @ $Revision$
 */

//Для Chome есть плагин Postman, в нем удобно посылать запросы на сервер, можно указывать HTTP тип, параметры и многое другое

@Path("/book")
public class BookService
{
    //Ниже идут методы класса которые вызывает сервера приложений в соответствии с пришедшим запросом
    @Produces({"application/json", "application/xml"})  //Возможный MIME тип данных ответа, в запросе можно указать параметр Accept со значением MIME типа
                                                        //  и если это возможно сервер вернет представление объекта указанного типа, если ни чего не указывать
                                                        //  то вернется первый в списке тип, в данном случае json
    @Path("{symbol}")                                   //В пути запроса последним элеменом должен быть параметр (http://localhost:8080/myserver/rf_resources/book/Название книги)
                                                        //  в данном случае этот параметр равен "Название книги", это параметр быдет передан в список параметров метод
    @GET                                                //HTTP метод используемый запросом
    public Book getBook(@PathParam("symbol") String symbol) //Указываем параметр
    {
        return new Book(symbol); //Возвражаем POJO объект анноированный @XmlRootElement
    }

    @Path("/it/{code},{isbn}") //Удлинняем путь и добавляем 2 параметра передаваемые в пути (http://localhost:8080/myserver/rf_resources/book/it/код,исбн)
                               //  можно сделать и так @Path("/it/{code}/{isbn}"), тогда запрос тоже будет через слэш (http://localhost:8080/myserver/rf_resources/book/it/код/исбн)
                               //Для параметров так же можно задавать регулярное выражение, @Path("/it/{code:[a-zA-Z]},{isbn}"), еще надо дорабобраться как правильно это делать
    @GET
    public String getBook(@PathParam("isbn") String isbn, @PathParam("code") String code) //Параметры из пути
    {
        return new String(code +"-" + isbn);
    }

    @Produces({"application/json", "application/xml"})
    @Consumes("application/x-www-form-urlencoded")  //Указываем MIME тип данных запроса, этот MIME тип лучше всего подходит для получения параметров с формы похожие на (путь?p=23)
    @POST
    public List<Book> existBook(@FormParam("par") String par) //Параметры из формы (в плагине Postman, нужно выбрать "Body" и выбрать "x-www-form-urlencoded",
                                                          // путь такой http://localhost:8080/myserver/rf_resources/book)
    {
        return Arrays.asList(new Book[]{new Book(par), new Book("Second")}); //Вернем список объектов
    }

    //Получаем POJO, в pospman указываем
    //http://localhost:8080/myserver/rf_resources/book/add.book
    //<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    //<book>
    //    <name>Книга из postman</name>
    //</book>
    //Можно и json,
    //в Content-Type указать    application/json ;charset=UTF-8
    //{
    //    "name": "Название книги"
    //}
    @Consumes({"application/xml", "application/json; charset=UTF-8"})
    @Produces({"application/xml", "application/json; charset=UTF-8"})
    @Path("/add.book")
    @POST
    public Book createBook(Book newBook)
    {
        return newBook;
    }

}
