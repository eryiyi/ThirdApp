package com.example.thirdapp.bean;

import java.io.Serializable;

public class TravelBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String image;
	public String content;
	public String title;
	public long release_time;
	public int good;
	public int collection;
	public int conmment_num;
	
	public TravelBean(){
		super();
	}
	
	public TravelBean(String image, String title, int good, int collection, int conmment_num){
		this.image = image;
		this.title = title;
		this.good = good;
		this.collection = collection;
		this.conmment_num = conmment_num;
	}
}
