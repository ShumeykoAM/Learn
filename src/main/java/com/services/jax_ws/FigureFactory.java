package com.services.jax_ws;

/**
 * @author kot
 * @ created 19.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class FigureFactory
{
    public static Figure createFigure(String type, StringBuilder description, int[] values)
    {
        if (type.equals(PrintFigureService.CIRCLE))
        {
            description.append("Radius is " + values[0] + ". ");
            return new Circle(type, values[0]);
        }
        else if (type.equals(PrintFigureService.RECTANGLE))
        {
            description.append("First side is " + values[0] + ", second side is " + values[1] + ". ");
            return new Rectangle(type, values[0], values[1]);
        }
        return null;
    }
}
