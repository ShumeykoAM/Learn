package struts.users;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kot
 * @ created 26.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class UserAccountForm
	extends ActionForm
{
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{	}
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		return errors;
	}

	private String userId = "";
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
