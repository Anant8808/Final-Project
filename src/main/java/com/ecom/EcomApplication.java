package com.ecom;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ecom.model.Role;
import com.ecom.repositories.RoleRepository;

@SpringBootApplication
public class EcomApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);
	}
	@Bean
	public ModelMapper mapper()
	{
		
		return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		
		
		
		try
		{
			
			Role role1=new Role();
			
			role1.setId(108);
			role1.setName("ROLE_ADMIN");
			
			Role role2=new Role();
			
			role2.setId(208);
			role2.setName("ROLE_NORMAL");
			
			
			List<Role> roles=new ArrayList<>();
			
			roles.add(role1);
			roles.add(role2);
			roleRepository.saveAll(roles);
			
		}
		
		catch(Exception e)
		{
			
			System.out.print("User Already there");
		}
		
		
		
		
	}
}
