package com;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;


//!!! TODO Внимание из за того что непонятно как заставить Weld тянуть настройки из test\META-INF\beans.xml, надо
//!!! TODO    вручную подсовывать тестовый beans.xml в папку main\META-INF

public class WeldJUnit4Runner
    extends BlockJUnit4ClassRunner
{

    private final Class<?> klass;
    private final Weld weld;
    private final WeldContainer container;

    public WeldJUnit4Runner(final Class<Object> klass) throws InitializationError
    {
        super(klass);
        this.klass = klass;
        this.weld = new Weld();
        this.container = weld.initialize();
    }

    @Override
    protected Object createTest() throws Exception
    {
        final Object test = container.instance().select(klass).get();
        return test;
    }

    @Override
    public void run( final RunNotifier notifier )
    {
        try
        {
            super.run( notifier );
        }
        finally
        {
            if ( container != null )
            {
                weld.shutdown();
            }
        }
    }

}