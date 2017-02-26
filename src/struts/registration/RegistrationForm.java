package struts.registration;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kot
 * @ created 25.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationForm
	extends ActionForm
{
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		userId = password = password2 = "";
	}
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if(userId.equals(""))
			errors.add("userId", new ActionMessage("registration.errors.user_id"));
		if(password.equals(""))
			errors.add("password", new ActionMessage("registration.errors.password"));
		else if(!password2.equals(password))
			errors.add("password2", new ActionMessage("registration.errors.password2"));
		return errors;
	}

	//Поля формы
	private String userId = "";
	private String password = "";
	private String password2 = "";

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
	public String getPassword2()
	{
		return password2;
	}
	public void setPassword2(String password2)
	{
		this.password2 = password2;
	}
}
