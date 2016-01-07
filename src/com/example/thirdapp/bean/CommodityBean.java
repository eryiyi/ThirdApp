package com.example.thirdapp.bean;

import java.io.Serializable;

public class CommodityBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String letter;
	public int image;
	public String imagebig;
	public String promotion;
	public String name;
	public String price;
	public String originalprice;
	
	public CommodityBean(){
		super();
	}
	
	public CommodityBean(String imagebig, String promotion, String name, String price, String originalprice, int image, String letter){
		this.imagebig = imagebig;
		this.promotion = promotion;
		this.name = name;
		this.price = price;
		this.originalprice = originalprice;
		this.letter = letter;
		this.image = image;
	}
}
