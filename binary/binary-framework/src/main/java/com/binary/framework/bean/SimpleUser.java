package com.binary.framework.bean;

public class SimpleUser implements User {
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String userId;
	private String userCode;
	private String userName;
	private String loginCode;
	private String authCode;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginCode() {
		return loginCode;
	}
	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends User> T owner(Class<T> ownerType) {
		return (T)this;
	}
	

	
	

}
