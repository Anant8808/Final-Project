package com.ecom.payload;

public class LoginResponse {

	private UserDto user;

	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public LoginResponse(UserDto user) {
		super();
		this.user = user;
	}
}
