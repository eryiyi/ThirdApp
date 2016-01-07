package com.example.thirdapp.bean;

import java.io.Serializable;

public class CategoryBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String categoryimg;
	public String categoryname;
	public int type_id;
	
	public CategoryBean(){
		super();
	}
	
	public CategoryBean(String categoryimg, String categoryname){
		this.categoryimg = categoryimg;
		this.categoryname = categoryname;
	}
}
