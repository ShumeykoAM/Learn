package com.example.jdbc.sqlite;

import java.sql.*;

/**
 * @author sbt-shumeyko-am
 * @ created 09.10.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
	public static void main(String[] args)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			String path = Main.class.getResource("/com/example/jdbc/db_sqlite.db").getPath().substring(1);
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path))
			{
				Statement statement = connection.createStatement();
				try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Students"))
				{
					while (resultSet.next())
					{
						int id = resultSet.getInt("_id");
						String string = resultSet.getString("Name");
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
}
