package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
	public static Connection getConnection(ConnectionConfiguration configuration) throws SQLException {
			return DriverManager.getConnection(configuration.getConnectionString(),
										configuration.getConnectionProperties());
	}
}
