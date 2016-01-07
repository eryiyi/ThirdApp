package com.example.thirdapp.bean;

import java.io.Serializable;

public class ActivityBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String actimage;
	public String actname;
	public String actdescription;
	public long acttime;
	public String info;
	public String pic0;
	public String pic1;
	public String pic2;
	public String pic3;
	public String address;
	
	public ActivityBean(){
		super();
	}
	
	public ActivityBean(String actimage, String actname, String actdescription, long acttime){
		this.actimage = actimage;
		this.actname = actname;
		this.actdescription = actdescription;
		this.acttime = acttime;
	}
}
