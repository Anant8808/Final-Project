package com.ecom.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.payload.LoginRequest;
import com.ecom.payload.LoginResponse;
import com.ecom.payload.UserDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	 public ResponseEntity<LoginResponse> login(
	            @RequestBody LoginRequest request
	    ) throws Exception {

	        //authenticate
	        this.authenticateUser(request.getUsername(), request.getPassword());
	        ///age ka kam
	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
               
	         LoginResponse response=new LoginResponse();
	         
	         UserDto dto=this.mapper.map(userDetails, UserDto.class);
	        response.setUser(dto);
	        return new ResponseEntity<>(response, HttpStatus.OK);


	    }
	
	private void authenticateUser(String username, String password) throws Exception {


        try {
            //authenticate
            manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {

            throw new Exception("Invalid username or Password !!");

        } catch (DisabledException e) {
            throw new Exception("User is not active !!");
        }


    }


}
