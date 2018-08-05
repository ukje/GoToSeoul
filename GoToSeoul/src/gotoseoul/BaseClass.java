package gotoseoul;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseClass
{

	public Connection getConn() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");

		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3366/gotoseoul?useUnicode=true&characterEncoding=utf8",
				"admin", 
				"admin");

	}
}
