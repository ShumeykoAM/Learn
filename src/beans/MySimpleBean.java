// 8
/*
* JavaBean - это класс оформленный по определенным правилам, объект которого создастся с помощью специального
*   синтаксиса в .jsp файлах
*/
package beans;

import java.io.Serializable;

//В данном примере этот класс используется в JSP_Servlet.jsp
public class MySimpleBean
  implements Serializable //Bean должен быть сериализуемым
{
  public MySimpleBean()   //Bean должен иметь конструктор без параметров
  {
    nameUser = "Мотильда";
  }
  
  //.jsp файл обращается к объектам класса Bean с помощью специального синтаксиса, по этому Bean должен иметь
  //  private поля и геттеры и\или сеттеры для этих полей
  
  private String nameUser;     //Поля доступные из .jsp должны именоваться с маленькой буквы и иметь геттер и или сеттер
  public String getNameUser()  //  имя геттера для поля, доступного из .jsp, должно иметь префикс get и имя поля с большой буквы
  {
    return nameUser;
  }
  public void setNameUser(String name) //  имя сеттера для поля, доступного из .jsp, должно иметь префикс get и имя поля с большой буквы
  {
    this.nameUser = name;
  }
  
  public int sum(int arg1, int arg2)
  {
    return arg1 + arg2;
  }
}
