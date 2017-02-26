package struts.registration;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kot
 * @ created 25.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationAction
	extends Action
{
	//В данном классе нельзя объявлять поля, так как один и тот же объект
	//   этого класса может использоваться для разных объектов ActionForm

	@Override public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RegistrationForm registrationForm = (RegistrationForm) form;
		if (!registrationForm.getUserId().equals("kot"))
		{
			return mapping.findForward("success");
		}
		else
		{
			ActionMessages errors = new ActionMessages();
			errors.add("userId", new ActionMessage("registration.errors.user_id_busy"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
	}

	
}
