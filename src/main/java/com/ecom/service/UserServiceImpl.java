package com.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.model.User;
import com.ecom.payload.UserDto;
import com.ecom.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDto create(UserDto userDto) {
		
		User user =this.mapper.map(userDto, User.class);
		 User createdUser =this.userRepository.save(user);
		 
		 return this.mapper.map(createdUser,UserDto.class);
	}

	@Override
	public UserDto update(UserDto t, int userId) {
		
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with userId " + userId));

		u.setName(t.getName());
		u.setEmail(t.getEmail());
		u.setPassword(t.getPassword());
		u.setAbout(t.getAbout());
		u.setAddress(t.getAddress());
		u.setActive(t.isActive());
		u.setGender(t.getGender());
		u.setCreateAt(t.getCreateAt());
		u.setPhone(t.getPhone());

		User updatedUser = this.userRepository.save(u);

		return this.mapper.map(updatedUser,UserDto.class);
		
		
	}

	@Override
	public void delete(int userId) {
		
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with userid " + userId));
		this.userRepository.delete(u);
	}

	@Override
	public List<UserDto> getAll() {
		List<User> allUser = this.userRepository.findAll();
		List<UserDto> allDtos = allUser.stream().map(user -> this.mapper.map(user,UserDto.class)).collect(Collectors.toList());
		return allDtos;
	}

	@Override
	public UserDto getByUserId(int userId) {
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user id  " + userId));
	
		return this.mapper.map(u,UserDto.class);
	}

	@Override
	public UserDto getByEmail(String email) {
		User user = this.userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException(" User with email not found in database"));
		return this.mapper.map(user,UserDto.class);
	}
	

}
