package com.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Qualifier;

//Контейнер распознает класс как CDI если он
//    не является нестатическим внктренним классом
//    это конкретный класс либо класс, имеющий аннотацию @Decorator
//    имеет констуктор без параметров или конструктор помеченный @Inject

//Управляемый компонент CDI, объект этого класса должен создавать контейнер, он же внедрит компоненты
public class BookService
{
    private NumberGenerator numberGenerator2;
    private NumberGenerator numberGenerator3;

    //Во все помеченные @Inject точки внедрения контейнер поместит ссылку на внедряемый компонент, он же и создаст этот компонент

    //Можно создавать 3 точки внедрения
    //1)   точка внедрения в поле
    @Inject
    @EightDigits//
    private NumberGenerator numberGenerator1;
    //2)   точка внедрения в сеттере
    @Inject
    public void setNumberGenerator(@ThirteenDigits NumberGenerator numberGenerator2)
    {
        this.numberGenerator2 = numberGenerator2;
    }
    //3)   точка внедрения в конструкторе
    @Inject
    public BookService(@EightDigits NumberGenerator numberGenerator3)
    {
        this.numberGenerator3 = numberGenerator3;
    }

    //Контейнер так же будет вызывать методы помеченные @PostConstruct @PreDestroy
    //@PostConstruct - метод вызывается контейнером сразу после конструирования объекта и внедрения всех зависимостей
    @PostConstruct
    private void fPC()
    {    }
    //@PreDestroy - вызывается контейнером непосредственно перед разрушением объекта
    @PreDestroy
    private void fPD()
    {    }

    //@Transactional
    public Book createBook(String name)
    {
        Book book = new Book(name);
        book.setNumber(numberGenerator1.generateNumber());
        return book;
    }


}

//Опишем интерфейс внедряемого компонента
interface NumberGenerator
{
    int generateNumber();
}

//Квалификаторы для однозначного определения внедряемого компонента
//  если имеется несколько реализаций внедряемого компонента, то нужно использовать
//  квалификаторы (пользовательские квалификаторы) для разрешения неоднозначности,
//  квалификаторы можно реализовывать с параметрами для того что бы не размножать квалификаторы
//  Так же существует квалификатор Default
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@interface EightDigits
{   }

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@interface ThirteenDigits
{   }


//Реализацию внедряемых компонентов можно помечать пользовательскими квалификаторами @Qualifier
@EightDigits
class IssnGenerator
    implements NumberGenerator
{
    @Override public int generateNumber()
    {
        return 8;
    }
}
@ThirteenDigits
class IsbnGenerator
    implements NumberGenerator
{
    @Override public int generateNumber()
    {
        return 13;
    }
}