package com.example.thirdapp.bean;

import java.io.Serializable;

public class ToBeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String image;
	public String dstname;
	public String dstcolor;
	public String price;
	public String number;
	
	public ToBeBean(){
		super();
	}
	
	public ToBeBean(String name, String image, String dstname, String dstcolor, String price, String number){
		this.name = name;
		this.image = image;
		this.dstname = dstname;
		this.dstcolor = dstcolor;
		this.price = price;
		this.number = number;
	}
}
