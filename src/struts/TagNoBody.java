package struts;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Если tag в tld библиотеке не будет иметь тело при использовании в JSP, тоесть между открывающим и
 *   закрывающим тегом не будет текста, то надо наследовать наш реализующий Bean от
 *   javax.servlet.jsp.tagext.TagSupport
 */
public class TagNoBody
  extends TagSupport
{
  public TagNoBody()
  {
    
  }
  //По правилам оформления Bean создаем свойства и их геттеры и сеттеры
  private String propStr = null;
  public String getPropStr()
  {
    return propStr;
  }
  public void setPropStr(String property)
  {
    this.propStr = property;
  }

  //А здесь мы делаем вывод в JSP станице. в месте задания свойства
  private String formatOut;
  public void setFormatOut(String formatOut)
  {
    this.formatOut = formatOut;
    try
    {
      super.pageContext.getOut().print(propStr);
    } catch(IOException e)
    {
      e.printStackTrace();
    }
  }
  
}
