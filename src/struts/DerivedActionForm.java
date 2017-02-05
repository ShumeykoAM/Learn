package struts;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

//В парадигме MVC - это контроллер простой валидации
public class DerivedActionForm
    extends ActionForm   //Наследники ActionForm должны описываться по правилам JavaBean
{
	public DerivedActionForm()
	{
		userId = password = "";
	}

	//Можно переопределить этот метод для первичной (простой) проверки параметров переданных в запросе
	//  этот метод вызывается struts
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors actionErrors = new ActionErrors();
		if(userId == null || userId.isEmpty())
		    actionErrors.add("userID", new ActionMessage("reg.error.userid.missing"));
		if(password == null || password.isEmpty())
			actionErrors.add("password", new ActionMessage("reg.error.password.missing"));
		//Если actionErrors не пуст, то страница будет снова ререрисована, если на ней используются теги <html:errors ...>
		return actionErrors;  //  то будет отображен текст об ошибке
	}

	//По правилам Java Beans могут быть описаны геттеры и сеттеры для доступных свойств
	private String userId, password; //Нпример идентификатор и пароль пользователя
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
  
  
}
