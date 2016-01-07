package com.image.view;

public class Content {

	private String letter;
	private String name;
	private int image;
	private int imagebig;
	private String price;
	private String originalprice;
		
	public Content(String letter, String name) {
		super();
		this.letter = letter;
		this.name = name;
	}
	
	public Content(String letter, String name, int image, int imagebig) {
		super();
		this.letter = letter;
		this.name = name;
		this.image = image;
		this.imagebig = imagebig;
	}
	
	public Content(String letter, String name, int image, int imagebig, String price, String originalprice) {
		super();
		this.letter = letter;
		this.name = name;
		this.image = image;
		this.imagebig = imagebig;
		this.price = price;
		this.originalprice = originalprice;
	}
	
	public String getLetter() {
		return letter;
	}
	
	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getOriginalprice() {
		return originalprice;
	}
	
	public void setOriginalprice(String originalprice) {
		this.originalprice = originalprice;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getImage() {
		return image;
	}
	
	public void setImage(int image) {
		this.image = image;
	}
	
	public int getImageBig() {
		return imagebig;
	}
	
	public void setImageBig(int imagebig) {
		this.imagebig = imagebig;
	}
}
