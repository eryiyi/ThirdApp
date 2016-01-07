package com.example.thirdapp.bean;

import java.io.Serializable;

public class WalletBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int bankimage;
	public String bankclass;
	public String banknumber;
	
	public WalletBean(){
		super();
	}
	
	public WalletBean(int bankimage, String bankclass, String banknumber){
		this.bankimage = bankimage;
		this.bankclass = bankclass;
		this.banknumber = banknumber;
	}
}
