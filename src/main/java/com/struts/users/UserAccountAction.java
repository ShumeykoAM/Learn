package com.struts.users;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kot
 * @ created 26.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class UserAccountAction
	extends Action
{
	//В данном классе нельзя объявлять поля, так как один и тот же объект
	//   этого класса может использоваться для разных объектов ActionForm

	@Override public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserAccountForm registrationForm = (UserAccountForm) form;
		return mapping.findForward("exit");
	}
}
