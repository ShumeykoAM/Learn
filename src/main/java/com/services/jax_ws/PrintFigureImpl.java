package com.services.jax_ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author kot
 * @ created 19.03.2017
 * @ $Author$
 * @ $Revision$
 */
@WebService(endpointInterface = "com.services.jax_ws.PrintFigureImpl")
public class PrintFigureImpl
    implements PrintFigureService
{
    @Override
    @WebMethod
    public String showInfo(String type, int... values)
    {
        StringBuilder description = new StringBuilder();
        description.append("This is a " + type + ". ");
        Figure figure = FigureFactory.createFigure(type, description, values);
        description.append("Square of " + figure.getName() + " is " + figure.square());
        return description.toString();
    }
}
