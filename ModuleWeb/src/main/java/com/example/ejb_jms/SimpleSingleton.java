package com.example.ejb_jms;

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
	private String commonField;

	public String getCommonField()
	{
		return commonField;
	}

	@PostConstruct
	private void construct()
	{
		commonField = "Any common resource";
	}

	@PreDestroy
	private void destruct()
	{
		commonField = null;
	}
}
