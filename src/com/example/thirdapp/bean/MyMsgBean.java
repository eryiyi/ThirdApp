package com.example.thirdapp.bean;

import java.io.Serializable;

public class MyMsgBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String content;
	public String publictime;
	
	public MyMsgBean(){
		super();
	}

	public MyMsgBean(String content){
		this.content = content;
	}
	
	public MyMsgBean(String content, String publictime){
		this.content = content;
		this.publictime = publictime;
	}
}
