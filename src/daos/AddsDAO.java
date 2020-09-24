package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.ConnectionUtils;
import jdbc.MySQLConnection;
import models.Add;

public class AddsDAO {
	
	public AddsDAO() {
	}

	public void createAdd(Add add) throws IllegalArgumentException, SQLException {
		Connection connection = ConnectionUtils.getConnection(
				new MySQLConnection());
		
		String query = "INSERT INTO announce (id, model, type, length, price, urlAdd, urlPhoto, year, location) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";	
		
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			
			statement.setString(1, add.getId());
			statement.setString(2, add.getModel());
			statement.setString(3, add.getType());
			statement.setDouble(4, add.getLength());
			statement.setInt(5, add.getPrice());
			statement.setString(6, add.getUrlAdd());
			statement.setString(7, add.getUrlPhoto());
			statement.setInt(8, add.getYear());
			statement.setString(9, add.getLocation());
			int result = statement.executeUpdate();
			
		}catch (SQLException exc) {
			throw new RuntimeException(exc);
		}
	}

	public void deleteAdds() throws IllegalArgumentException, SQLException {
		
		Connection connection = (Connection) ConnectionUtils.getConnection(
				new MySQLConnection());
		
		String query = "DELETE FROM announce WHERE 1";	
		
		try (PreparedStatement statement = connection.prepareStatement(query)) {
		
			statement.execute();
			
		}catch (SQLException exc) {
			throw new RuntimeException(exc);
		}
		
	}

}

