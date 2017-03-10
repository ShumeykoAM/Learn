package com.services;

import org.junit.*;

import java.lang.reflect.Method;

/*
* Обычно тестируются public методы класса, если при этом private метод не задействуется,
*   возможно он вообще не нужен в классе, но если сильно нужно, то можно протестировать и
*   package метод, распологаем класс теста в томже пакете или через рефлексию
*   protected методы с помощью наследования или рефлексии
*   private методы через рефлексию
*/

//@Ignore("Sometimes you want temporary disable all tests in class ")
public class TestService1
    extends Service1 //Тестируемый класс
{
    //Nногда нужно отключить тест (@Test), делается при помощи аннотации @org.junit.Ignore
    //@Ignore("Sometimes you want temporary disable test @Test")
    @Test //Точка входа в сам тест (тестов, точек входа, может быть несколько)
    public void testMessage()
    {
        //Тестирование private метода
        int expected_value = 1;
        final String namePrivateMethod = "serviceMethod_private";
        try
        {
            //Пример использования рефлексии
            Method method = Service1.class.getDeclaredMethod(namePrivateMethod, int.class); //Nмя метода и типы параметров
            method.setAccessible(true);                            //Разрешаем вызов private protected и package методов
            int result = (int)method.invoke(this, 1);  //Вызываем метод и получаем возвращаемое значение
            Assert.assertEquals(expected_value, result); //Если ожидаемое значение не совпадет с действительным, то тест считается не пройденным
        } catch(Exception e)
        {
            //У класса Assert много static методов для вывода ошибок теста
            Assert.fail("Method " + namePrivateMethod + " not found.");
        }
        //Тестирование остальных методов
        Assert.assertEquals(2, this.serviceMethod_protected(2));
        Assert.assertEquals(3, this.serviceMethod_package(3));
        Assert.assertEquals(4, this.serviceMethod_public(4));
        //System.out.println("MESSAGE " + str);
    }
    
    //Следующие 2 метода имеет смыслс использовать когда у нас один тест (@Test), или
    //   когда ресурсы выделяются полностью одинаково для всех тестов (@Test) текущего класса
    @Before //Nспользуется для инициализации окружения теста, перед запуском каждого теста (@Test)
    public void beforeTest()
    {
        
    }
    @After //Nспользуется для деинициализации теста, освобождения ресурсов, после прохождения каждого теста (@Test)
    public void afterTest()
    {
        //Вызовется даже если @Before или @Test бросил исключение
    }
    
    //В одном классе может быть несколько тестов, в таком случае больше подходит инициализация ресурсов для класса (см. ниже)
    @Test //Точка входа в еще один тест в данном классе
    public void testMessage2()
    {
        
    }
    //Nспользуется для инициализации класса теста, для единого выделения ресурсов для нескольких тестов (@Test) в классе
    @BeforeClass
    public static void beforeClassTest()
    {
        
    }
    //Освобождение ресурсов выделенных для класса теста, после прохождения всех тестов класса
    @AfterClass
    public static void afterClass()
    {
        //Вызовется даже если @BeforeClass метод бросил исключение
    }
    
    //@Test может иметь параметр expected в котором можно перечислить допустимые классы исключений
    //  которые может бросать тест и это не будет считаться ошибкой теста
    //@Test может иметь параметр timeout в миллисекундах, если тест работает дольше, то он прерывается с ошибкой
}