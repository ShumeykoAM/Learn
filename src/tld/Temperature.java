package tld;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Если tag в tld библиотеке БУДЕТ (или может) иметь тело при использовании в JSP, тоесть между открывающим и
 *   закрывающим тегом может быть текст, то надо наследовать наш реализующий Bean от
 *   javax.servlet.jsp.tagext.BodyTagSupport
 */
public class Temperature
  extends BodyTagSupport
{
  //По правилам оформления Bean создаем свойства и их геттеры и сеттеры
  public Temperature()
  {
  
  }
  public void setTo(String to)
  {
    this.to = to;
  }
  private String to = "c";
  
  
  //Метод вызовется когда у тега есть тело, после вызова всех сеттеров
  @Override
  public int doAfterBody() throws JspException
  {
    BodyContent bc = getBodyContent();
    Double temperature = Double.parseDouble(bc.getString());
    switch(to)
    {
      case "c":
      case "C":
        temperature = temperature;
        break;
      case "K":
      case "k":
        temperature += 273.0;
    }
    JspWriter out = bc.getEnclosingWriter();
    try
    {
      //А здесь мы делаем вывод в JSP станице. в месте тела тега
      out.print(temperature);
    } catch(IOException e)
    {    }
    return EVAL_PAGE;
  }
  
}
