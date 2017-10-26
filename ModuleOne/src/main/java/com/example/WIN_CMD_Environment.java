package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Kot
 * @ created 26.10.2017
 * @ $Author$
 * @ $Revision$
 */
public class WIN_CMD_Environment
{
	//Но для подхвата джава машиной и др процессами, возможно нужна перезагрузка.
	public static void main(String[] args) throws Exception
	{
		setSystemEnvironment("AName1", "какое то значение1");
		setUserEnvironment("AName1", "какое то значение2");
	}

	static void setSystemEnvironment(String name, String value) throws IOException
	{
		String command = "REG ADD \"HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment\" /v \"" + name + "\" /d \"" + value + "\" /f";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true)
		{
			line = r.readLine();
			if (line == null)
			{
				break;
			}
			System.out.println(line);
		}
	}
	static void setUserEnvironment(String name, String value) throws IOException
	{
		String command = "REG ADD \"HKEY_CURRENT_USER\\Environment\" /v \"" + name + "\" /d \"" + value + "\" /f";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true)
		{
			line = r.readLine();
			if (line == null)
			{
				break;
			}
			System.out.println(line);
		}
	}
}
