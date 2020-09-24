package jdbc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class MySQLConnection implements ConnectionConfiguration {
	
	private static final String USER = "root";
	private static final String PASSWD = "";
	private static final int PORT = 3306;
	private static final String DBNAME = "barcosocasion";
	private static final String SERVER = "localhost";
	private final Map<String, String> properties;

	public MySQLConnection() throws IllegalArgumentException {
		this.properties = new HashMap<String, String>();
	}
	
	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}
	
	public String putProperty(String property, String value) {
		return this.properties.put(property, value);
	}
	
	public String getProperty(String property) {
		return this.properties.get(property);
	}
	
	public String removeProperty(String property) {
		return this.properties.remove(property);
	}

	@Override
	public String getConnectionString() {
		final StringBuilder sb = new StringBuilder("jdbc:mysql://");
		
		if (SERVER != null)
			sb.append(SERVER);
		
		
		if (PORT >= 0)
			sb.append(':').append(PORT);
		
		sb.append('/');
		
		if (DBNAME != null)
			sb.append(DBNAME);
		
		boolean isFirst = true;
		for (Map.Entry<String, String> property : this.properties.entrySet()) {
			sb.append(isFirst ? '?' : '&');
			isFirst = false;
			
			sb.append(property.getKey()).append("=")
				.append(property.getValue());
		}
		
		return sb.toString();
	}

	@Override
	public Properties getConnectionProperties() {
		final Properties properties = new Properties();
		
			properties.put("user", USER);
			properties.put("password", PASSWD);
		
		return properties;
	}
}
