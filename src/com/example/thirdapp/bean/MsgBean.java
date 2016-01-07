package com.example.thirdapp.bean;

import java.io.Serializable;

public class MsgBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int msgimage;
	public String msgtext;
	
	public MsgBean(){
		super();
	}
	
	public MsgBean(int msgimage, String msgtext){
		this.msgimage = msgimage;
		this.msgtext = msgtext;
	}
}
