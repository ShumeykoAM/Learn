package struts;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//В парадигме MVC - это контроллер бизнеслогики и комплексной валидации
public class DerivedAction
    extends Action
{
	//!!! Важно понимать что нельзя определять свойства у наследников Action
	//int param1; //Так делать неправильно, нет гарантии что именно этот экземпляр класса будет
	//   принадлежать текущему запросу, сервер может создавать пулы объектов этого класса для оптимизации

	//Этот метод вызывается struts после того как прошла валидация в ActionForm
	@Override
	public ActionForward execute(ActionMapping mapping,
	                             ActionForm form,
	                             HttpServletRequest request,
	                             HttpServletResponse response) throws Exception
	{
		DerivedActionForm actionForm = (DerivedActionForm)form;
		String userId = actionForm.getUserId();
		String password = actionForm.getPassword();

		//Комплексная валидация
		if(!checkUserIdFree(userId))
		{
			ActionMessages errors = new ActionMessages();
			errors.add("userID", new ActionMessage("reg.error.userid.exists"));
			saveErrors(request, errors);
			return mapping.getInputForward(); //Переоткрыть страницу (если на ней используются теги <html:errors ...>
		}	                                  //  то на странице будут отображены добавленные выше ошибки

		//Бизнес логика: модификация данных в БД и др.
		//...

		//Укажем куда надо двигаться дальше
	    return mapping.findForward("success"); //Следующая страница прописана в struts-config.xml
	}

	//Вспомогательные методы
	private boolean checkUserIdFree(String userId)
	{
		return !userId.equals("kot");
	}
}
