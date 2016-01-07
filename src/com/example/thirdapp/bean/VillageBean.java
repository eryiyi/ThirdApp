package com.example.thirdapp.bean;

import java.io.Serializable;

public class VillageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String area;
	public String address;
	public String community_name;
	public String number;
	public int community_id;
	
	public VillageBean(){
		super();
	}
	
	public VillageBean(String area, String address, String community_name){
		this.area = area;
		this.address = address;
		this.community_name = community_name;
	}
}
