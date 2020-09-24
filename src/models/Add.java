package models;

public class Add {
	
	private String id;
	private String model;
	private String type;
	private double length;
	private int price;
	private String urlAdd;
	private String urlPhoto;
	private int year;
	private String location;
	
	public Add() {
	}
	
	public Add(String id, String model, String type,double length, int price, String urlAdd, 
			String urlPhoto, int year, String location) {
		
		this.id = id;
		this.model=model;
		this.type=type;
		this.length=length;
		this.price=price;
		this.urlAdd=urlAdd;
		this.urlPhoto=urlPhoto;
		this.year=year;
		this.location=location;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getUrlAdd() {
		return urlAdd;
	}

	public void setUrlAdd(String urlAdd) {
		this.urlAdd = urlAdd;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
