package com.example.thirdapp.bean;

import java.io.Serializable;

public class StudyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String stdimage;
	public String stdname;
	public String stdcontent;
	public String stdtime;
	public String stdchecknum;
	
	public StudyBean(){
		super();
	}
	
	public StudyBean(String stdimage, String stdname, String stdcontent){
		this.stdimage = stdimage;
		this.stdname = stdname;
		this.stdcontent = stdcontent;
	}
	
	public StudyBean(String stdimage, String stdname, String stdcontent, String stdtime, String stdchecknum){
		this.stdimage = stdimage;
		this.stdname = stdname;
		this.stdcontent = stdcontent;
		this.stdtime = stdtime;
		this.stdchecknum = stdchecknum;
	}
}
