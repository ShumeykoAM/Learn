package com.services.jax_ws;

/**
 * @author kot
 * @ created 19.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Rectangle
    extends Figure
{
    private int width;
    private int height;

    public Rectangle(String name, int width, int height)
    {
        super(name);
        this.width = width;
        this.height = height;
    }

    @Override
    public double square()
    {
        return width * height;
    }
}
