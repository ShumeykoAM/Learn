package com.services;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/*
* Обычно тестируются public методы класса, если при этом private метод не задействуется,
*   возможно он вообще не нужен в классе, но если сильно нужно, то можно протестировать и
*   package метод, распологаем класс теста в томже пакете или через рефлексию
*   protected методы с помощью наследования или рефлексии
*   private методы через рефлексию
*/

public class TestService1
    extends Service1 //Тестируемый класс
{
    //Nногда нужно отключить тест, делается при помощи аннотации @org.junit.Ignore
    //@Ignore("Sometimes you want temporary disable test")
    @Test
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
}