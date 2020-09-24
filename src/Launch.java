import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.SQLException;
import controllers.AddsController;
import daos.AddsDAO;
import jdbc.ConnectionUtils;
import scrapping.Cosasdebarcos;
import scrapping.Scraping;
import scrapping.YachtWorld;


public class Launch {
	
	public static void main(String[] args) throws SQLException {
	    long startTime = System.currentTimeMillis();

		//First empty DB
		AddsDAO daoAdds = new AddsDAO();
		AddsController controller = new AddsController(daoAdds);
		controller.deleteAdds();  
	    
		//Call to the method crearAnuncios of the website CosasdeBarcos
		Scraping CosasdeBarcos = new Cosasdebarcos(controller);
		Scraping YachtWorld = new YachtWorld(controller);

		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Total time: "+elapsedTime+" ms.");
	}
	
}
