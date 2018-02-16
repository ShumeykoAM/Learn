package com.tools.logger;

import com.google.common.base.Joiner;

import java.util.*;

/**
 * Структура хранения и вывода структурированного сообщения
 * @author kot
 * @ created 24.10.2017
 * @ $Author$
 * @ $Revision$
 */
public class LogMessage
{
	private Map<String, String> StructMessage = new HashMap<>();

	public LogMessage put(String key, String value)
	{
		StructMessage.put(key, value);
		return this;
	}

	public String getMessageString()
	{
		List<String> values = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		for (String key : StructMessage.keySet())
		{
			sb.append(key);
			sb.append(": [");
			sb.append(StructMessage.get(key));
			sb.append("]");
			values.add(sb.toString());
			sb.delete(0, sb.length());
		}
		return Joiner.on(",  ").join(values);
	}
}
