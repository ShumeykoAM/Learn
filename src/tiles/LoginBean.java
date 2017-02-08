package tiles;

import org.apache.struts.action.ActionForm;

/**
 * @author SBT-Shumeyko-AM
 * @ created 08.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class LoginBean
  extends ActionForm
{
  public LoginBean()
  {
    
  }
  
  public boolean getStatus()
  {
    return status;
  }
  
  public void setStatus(boolean status)
  {
    this.status = status;
  }
  
  private boolean status;
  
    
}
