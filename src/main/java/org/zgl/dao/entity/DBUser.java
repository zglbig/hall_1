package org.zgl.dao.entity;

import java.sql.Date;

public class DBUser {

	private Integer id;
	private Long uid;
	private String account;
	private String password;
	private String userName;
	private String baseInfo;
	private String signIn;
	private String weath;
	private String friends;
	private String giftBag;
	private String task;
	private Long generalizeUid;//推广人的uid
	private Date createTime;
	private Date lastEditTime;

	public String getBaseInfo(){
		return baseInfo;
	}
	public void setBaseInfo(String baseInfo){
		this. baseInfo = baseInfo;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this. password = password;
	}
	public String getTask(){
		return task;
	}
	public void setTask(String task){
		this. task = task;
	}
	public String getSignIn(){
		return signIn;
	}
	public void setSignIn(String signIn){
		this. signIn = signIn;
	}
	public String getGiftBag(){
		return giftBag;
	}
	public void setGiftBag(String giftBag){
		this. giftBag = giftBag;
	}
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this. id = id;
	}
	public String getWeath(){
		return weath;
	}
	public void setWeath(String weath){
		this. weath = weath;
	}
	public String getAccount(){
		return account;
	}
	public void setAccount(String account){
		this. account = account;
	}
	public String getFriends(){
		return friends;
	}
	public void setFriends(String friends){
		this. friends = friends;
	}

	public Long getGeneralizeUid() {
		return generalizeUid;
	}

	public void setGeneralizeUid(Long generalizeUid) {
		this.generalizeUid = generalizeUid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
