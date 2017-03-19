package com.services.jax_ws;

/**
 * @author kot
 * @ created 19.03.2017
 * @ $Author$
 * @ $Revision$
 */
public abstract class Figure
{
    private String name;

    public Figure(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public abstract double square();
}
