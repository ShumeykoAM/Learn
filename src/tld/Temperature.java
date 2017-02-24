package tld;

import java.io.IOException;
import java.text.DecimalFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Created by Kot on 18.02.2017.
 */
public class Temperature
	extends BodyTagSupport
{
	/**
	 * Метод вызывается после вызова setter'ов и в нем можно вывести результирующее содержимое
	 * @return javax.servlet.jsp.tagext.Tag.EVAL_PAGE
	 * @throws JspException
	 */
	@Override public int doAfterBody() throws JspException
	{
		try
		{
			if(!to.equals("c") && !to.equals("C") && !to.equals("k") && !to.equals("K") &&
				!to.equals("f") && !to.equals("F"))
				throw new RuntimeException();
			BodyContent bodyContent = getBodyContent();
			Double temperature = Double.parseDouble(bodyContent.getString());
			switch(to)
			{
				case "c":
				case "C":
					temperature = temperature;
					break;
				case "K":
				case "k":
					temperature += 273.15;
				case "F":
				case "f":
					temperature = temperature * 1.8 + 32;
			}
			DecimalFormat myFormatter = new DecimalFormat("###.##");
			String tempFormat = myFormatter.format(temperature);
			JspWriter out = bodyContent.getEnclosingWriter();
			//А здесь мы делаем вывод в JSP станице. в месте тела тега
			out.print(tempFormat);
		}
		catch(IOException e)            {    }
		catch(NumberFormatException e)  {    }
		catch(RuntimeException e)       {    }
		return EVAL_PAGE;
	}

	public String getTo()
	{
		return to;
	}
	public void setTo(String to)
	{
		this.to = to;
	}
	private String to;
}
