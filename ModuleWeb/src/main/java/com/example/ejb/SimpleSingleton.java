package com.example.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author Kot
 * @ created 05.10.2017
 * @ $Author$
 * @ $Revision$
 */
@Startup
@Singleton
public class SimpleSingleton
{

	@PostConstruct
	public void construct()
	{

	}

	@PreDestroy
	public void destruct()
	{

	}
}
