package org.zgl.dao.entity;

public class DBAdmininfo {

	private java.sql.Date timer;
	private Integer topUp;
	private String online;
	private Integer registNum;
	private Integer id;
	private Long registUid;
	private String systemWeath;

	public java.sql.Date getTimer(){
		return timer;
	}
	public void setTimer(java.sql.Date timer){
		this. timer = timer;
	}
	public Integer getTopUp(){
		return topUp;
	}
	public void setTopUp(Integer topUp){
		this. topUp = topUp;
	}
	public String getOnline(){
		return online;
	}
	public void setOnline(String online){
		this. online = online;
	}
	public Integer getRegistNum(){
		return registNum;
	}
	public void setRegistNum(Integer registNum){
		this. registNum = registNum;
	}
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this. id = id;
	}

	public Long getRegistUid() {
		return registUid;
	}

	public void setRegistUid(Long registUid) {
		this.registUid = registUid;
	}

	public String getSystemWeath(){
		return systemWeath;
	}
	public void setSystemWeath(String systemWeath){
		this. systemWeath = systemWeath;
	}
}
