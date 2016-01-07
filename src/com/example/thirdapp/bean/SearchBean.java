package com.example.thirdapp.bean;

import java.io.Serializable;

public class SearchBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String searchtext;
	
	public SearchBean(){
		super();
	}
	
	public SearchBean(String searchtext){
		this.searchtext = searchtext;
	}
}
