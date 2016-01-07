package com.example.thirdapp.bean;

import java.io.Serializable;

public class HouseInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String houseimage;
	public String housedescription;
	public String housename;
	public String houseprice;
	public String accuracy;
	
	public HouseInfoBean(){
		super();
	}
	
	public HouseInfoBean(String houseimage, String housedescription, String housename, String houseprice, String accuracy){
		this.houseimage = houseimage;
		this.housedescription = housedescription;
		this.housename = housename;
		this.houseprice = houseprice;
		this.accuracy = accuracy;
	}
}
