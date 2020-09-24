package scrapping;
import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import controllers.AddsController;
import models.Add;

public class Cosasdebarcos extends Scraping {

	public final String urlCDBVeleros = "https://www.cosasdebarcos.com/veleros-en-venta-1/pagina-";
	public final String urlCDBsMotoras = "https://www.cosasdebarcos.com/barcos-a-motor-en-venta-2/pagina-";
	public final int maxPages = 10;

	AddsController controller;

	public Cosasdebarcos(AddsController controller) {
		this.controller = controller;
		this.scraping(urlCDBVeleros);
		this.scraping(urlCDBsMotoras);
	}
	
	//Scrap the information from the url 
	public void scraping(String urlScraping) {
		int priceCleaned;
		String idAdd = null;
		for(int i = 1;i<maxPages;i++) {
			String url = urlScraping + i;
			System.out.println(url);
			
			if (getStatusConnectionCode(url) == 200) {
				Document document = getHtmlDocument(url);
				Elements entradas = document.select("#listaBarcoOcasion > li > article > div");
				for (Element elem : entradas) {
					String tipo = elem.getElementsByClass("brand").select("span").first().text();
					String modelo = elem.getElementsByClass("brand").text().replaceAll(tipo, "");
					String urlAnuncio = elem.getElementsByClass("positionNav").attr("abs:href");
					String precio = elem.getElementsByClass("price-before").text();
					
					if(precio.contentEquals("Precio a consultar")) {
						priceCleaned = 0;
					}else {
						precio = precio.replace('€', ' ');
						precio = precio.replace('.', ' ');
						precio = precio.replaceAll("\\s+","");
						priceCleaned = Integer.parseInt(precio);
					}
					 
					int ano = Integer.parseInt(elem.getElementsByClass("fa-calendar").next().text());
					String eslora = elem.getElementsByClass("fa-ship").next().text();
					eslora = eslora.replace('m', ' ');
					eslora = eslora.replace('.', ' ');
					eslora = eslora.replaceAll("\\s+","");
					eslora = eslora.replace(',', '.');
					double esloraCleaned = Double.parseDouble(eslora);
					String urlFoto = elem.getElementsByClass("owlGalleryItem").attr("data-src");
					
					String location = "";
					Elements spanTags = elem.getElementsByClass("list-item-details__item--fw");
					boolean flag = false;
					for (Element spanTag : spanTags) {
						String text = spanTag.ownText();
						if(text != null && !text.equals("") && !text.equals(" ") && !flag) {
							location = text;
							location = location.replace(',', ' ');
							flag = true;
						}
				    }
					//Check if the add has all the information
					if(urlAnuncio != null && !urlAnuncio.equals("") && !urlAnuncio.equals(" ") && precio.length() <= 11
							&& !tipo.equals("") && !modelo.equals("") && !location.equals("")) {
						if(urlAnuncio.length() > 35) {
							idAdd = urlAnuncio.substring(30);
							idAdd = idAdd.substring(0, idAdd.length()-5);
						}
						Add add = new Add(idAdd, modelo, tipo, esloraCleaned, priceCleaned, urlAnuncio, urlFoto, ano, location);
						try {
							this.controller.createAdd(add);
						} catch (IllegalArgumentException e) {		
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else {
						System.out.println("¡Add without url or incomplete!");
					}
				}
			} else {
				System.out.println("Error HTTP: " + getStatusConnectionCode(url));
			}
		}
	}

	@Override
	public void scrapingExtended(String url, String type) {
	}

}