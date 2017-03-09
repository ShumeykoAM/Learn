package com.services;

/**
 * @author sbt-shumeyko-am
 * @ created 09.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Service1
{
    
    public int serviceMethod_public(int i)
    {
        return i;
    }
    /*package*/ int serviceMethod_package(int i)
    {
        return i;
    }
    protected int serviceMethod_protected(int i)
    {
        return i;
    }
    
    private int serviceMethod_private(int i)
    {
        return i;
    }
    
}
