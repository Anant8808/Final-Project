package com.ecom.payload;

public class LoginRequest {
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	private String username;
	private String password;

}
