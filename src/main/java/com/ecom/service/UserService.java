package com.ecom.service;


import java.util.List;

import com.ecom.payload.UserDto;

public interface UserService {

	
	
	
UserDto create(UserDto userDto);
	
	UserDto update(UserDto userDto,int userId);
	
	void delete(int userId);
	
	List<UserDto> getAll();
	
	UserDto getByUserId(int userId);
	
	UserDto getByEmail(String email);
	
}
