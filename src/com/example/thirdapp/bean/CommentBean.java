package com.example.thirdapp.bean;

import java.io.Serializable;

public class CommentBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int floor;
	public String username;
	public String commentcontent;
	public String commentdate;
	
	public CommentBean(){
		super();
	}
	
	public CommentBean(int floor, String promotion, String name, String price){
		this.floor = floor;
		this.username = promotion;
		this.commentcontent = name;
		this.commentdate = price;
	}
}
