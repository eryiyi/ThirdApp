package com.example.thirdapp.bean;

import java.io.Serializable;

public class ValueAgainstBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String vaimage;
	public String vaname;
	public String valimited;
	public String vaintegral;
	public String vapriceoriginal;
	
	public ValueAgainstBean(){
		super();
	}
	
	public ValueAgainstBean(String vaimage, String vaname, String valimited, String vaintegral, String vapriceoriginal){
		this.vaimage = vaimage;
		this.vaname = vaname;
		this.valimited = valimited;
		this.vaintegral = vaintegral;
		this.vapriceoriginal = vapriceoriginal;
	}
}
