package com.services.jax_ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author kot
 * @ created 19.03.2017
 * @ $Author$
 * @ $Revision$
 */

@WebService
public interface PrintFigureService
{
    public static String CIRCLE = "CIRCLE";
    public static String RECTANGLE = "RECTANGLE";

    @WebMethod
    public String showInfo(String type, int... values);
}
