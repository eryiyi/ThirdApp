package com.example.thirdapp.bean;

import java.io.Serializable;

public class MfBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int image;
	public String name;
	
	public MfBean(){
		super();
	}

	public MfBean(int image){
		this.image = image;
	}
	
	public MfBean(int image, String name){
		this.image = image;
		this.name = name;
	}
}
