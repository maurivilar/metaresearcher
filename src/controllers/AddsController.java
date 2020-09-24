package controllers;
import models.Add;

import java.sql.SQLException;

import daos.AddsDAO;

public class AddsController {
	private final AddsDAO dao;
	
	public AddsController(AddsDAO dao) {
		this.dao = dao;
	}

	public void createAdd(Add anuncio) throws IllegalArgumentException, SQLException {
		this.dao.createAdd(anuncio);
	}

	public void deleteAdds() throws IllegalArgumentException, SQLException {
		this.dao.deleteAdds();
	}
}
