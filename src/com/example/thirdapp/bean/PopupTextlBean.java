package com.example.thirdapp.bean;

import java.io.Serializable;

public class PopupTextlBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String popupname;
	
	public PopupTextlBean(){
		super();
	}
	
	public PopupTextlBean(String popupname){
		this.popupname = popupname;
	}
}
