package com.example.thirdapp.bean;

import java.io.Serializable;

public class ComListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String imagelist;
	public String namelist;
	public String salevolume;
	public String pricelist;
	
	public ComListBean(){
		super();
	}
	
	public ComListBean(String imagelist, String namelist, String salevolume, String pricelist){
		this.imagelist = imagelist;
		this.namelist = namelist;
		this.salevolume = salevolume;
		this.pricelist = pricelist;
	}
}
