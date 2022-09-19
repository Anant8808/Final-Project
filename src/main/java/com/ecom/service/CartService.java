package com.ecom.service;

import com.ecom.payload.CartDto;
import com.ecom.payload.ItemRequest;

public interface CartService {

	
	

		CartDto addItem(ItemRequest item, String userName);

		
		CartDto get(String userName);

		
		CartDto removeItem(String username, int productId);
}
