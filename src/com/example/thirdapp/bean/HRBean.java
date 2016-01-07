package com.example.thirdapp.bean;

import java.io.Serializable;

public class HRBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String hrtext;
	
	public HRBean(){
		super();
	}
	
	public HRBean(String hrtext){
		this.hrtext = hrtext;
	}
}
