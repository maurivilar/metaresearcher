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

public class YachtWorld extends Scraping {

	public final String urlYWVeleros = "https://www.yachtworld.es/core/listing/cache/searchResults.jsp?slim=quick&sm=3&currencyid=1004&searchtype=homepage&Ntk=boatsES&luom=127&cit=true&type=%28Sail%29&No=";
	public final String urlYWMotor = "https://www.yachtworld.es/core/listing/cache/searchResults.jsp?slim=quick&currencyid=1004&sm=3&searchtype=homepage&luom=127&Ntk=boatsES&cit=true&type=%28Power%29&No=";

	public final int maxPages = 10;

	AddsController controller;

	public YachtWorld(AddsController controller) {
		this.controller = controller;
		this.scrapingExtended(urlYWVeleros, "veleros");
		this.scrapingExtended(urlYWMotor, "motor");
	}
	
	//Scrap the information from the url 
	public void scrapingExtended(String urlScraping, String typ) {
		int priceCleaned;
		String idAdd = null;
		String type = "";
		if (typ.equals("veleros")) {
			type = "velero";
		} else {
			type = "motor";
		}

		for (int i = 0; i < maxPages * 50; i += 50) {
			String url = urlScraping + i;
			System.out.println("PAGINA: " + url);
			if (getStatusConnectionCode(url) == 200) {
				Document document = getHtmlDocument(url);
				Elements entradas = document.select("div.listing-container");
				for (Element elem : entradas) {
					String urlAnuncio = elem.getElementsByClass("positionNav").attr("abs:href");

					String precio = elem.getElementsByClass("price").text();
					if (precio.contentEquals("") || precio == null || precio.contentEquals("Llamar")) {
						priceCleaned = 0;
					} else {
						precio = precio.replace('E', ' ');
						precio = precio.replace('U', ' ');
						precio = precio.replace('R', ' ');
						precio = precio.replace('.', ' ');
						precio = precio.replace('*', ' ');
						precio = precio.replaceAll("\\s+", "");
						priceCleaned = Integer.parseInt(precio);
					}

					// Full info which includes length model and year -- Split into variables
					String fullInfo = elem.getElementsByClass("length feet").parents().get(0).text();
					String[] parcialInfo = fullInfo.split(" ");
					double length = Double.parseDouble(parcialInfo[0]);
					int year = Integer.parseInt(parcialInfo[2]);
					StringBuilder model = new StringBuilder();
					for (int j = 3; j < parcialInfo.length; j++) {
						model.append(parcialInfo[j]);
						model.append(" ");
					}
					String modelCleaned = model.toString();
					String urlFoto = elem.getElementsByClass("image").attr("style").substring(23).replaceAll("[');]",
							"");
					String location = elem.getElementsByClass("location").get(0).text();
					String urlAdd = elem.getElementsByTag("a").get(0).attr("abs:href");
					String[] id = urlAdd.split("/");
					String idCleaned = id[5];

					//Check if the add has all the information
					if (urlAdd != null && !urlAdd.equals("") && precio.length() <= 11
							&& !type.equals("") && !modelCleaned.equals("") && !location.equals("")) {
						Add add = new Add(idCleaned, modelCleaned, type, length, priceCleaned, urlAdd, urlFoto, year,
								location);
						try {
							this.controller.createAdd(add);
						} catch (IllegalArgumentException | SQLException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("¡Add without url or incomplete!");
					}

				}

			} else {
				System.out.println("Fallo HTTP: " + getStatusConnectionCode(url));
			}
		}
	}

	@Override
	public void scraping(String url) {
	}

}