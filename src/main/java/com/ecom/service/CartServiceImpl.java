package com.ecom.service;


import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.payload.CartDto;
import com.ecom.payload.ItemRequest;
import com.ecom.repositories.CartRepository;
import com.ecom.repositories.ProductRepository;
import com.ecom.repositories.UserRepository;

@Service
public class CartServiceImpl implements CartService {
	
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;


	@Override
	public CartDto addItem(ItemRequest item, String userName) {
		
		int productId = item.getProductId();
		int quantity = item.getQuantity();

		if (quantity <= 0) {
			throw new ResourceNotFoundException("Invalid Quantity !!");			
		}

		
		User user = this.userRepository.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException());

		
		Product product = this.productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
		if (!product.isStock()) {
			throw new ResourceNotFoundException("Product is out of stock");
		}
		System.out.println(product.getProductPrice());

		
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		cartItem.setTotalProductPrice();

		
		Cart cart = user.getCart();

	
		if (cart == null) {
			
			cart = new Cart();
			cart.setUser(user);

		}

		Set<CartItem> items = cart.getItems();

		AtomicReference<Boolean> flag = new AtomicReference<>(false);

		Set<CartItem> newItems = items.stream().map((i) -> {
			
			if (i.getProduct().getProductId() == product.getProductId()) {

				i.setQuantity(quantity);
				i.setTotalProductPrice();
				flag.set(true);
			}
			return i;
		}).collect(Collectors.toSet());

		
		if (flag.get()) {
			
			items.clear();
			items.addAll(newItems);
		} else {
			cartItem.setCart(cart);
			items.add(cartItem);
		}

		Cart updatedCart = this.cartRepository.save(cart);

		return this.mapper.map(updatedCart, CartDto.class);
	}

	@Override
	public CartDto get(String userName) {
		User user = this.userRepository.findByEmail(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given username !!"));

		Cart cart = this.cartRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found !!"));

		return this.mapper.map(cart, CartDto.class);
		
	}

	@Override
	public CartDto removeItem(String userName, int productId) {
		
		User user = this.userRepository.findByEmail(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given username !!"));


		Cart cart = user.getCart();

		Set<CartItem> items = cart.getItems();

		boolean result = items.removeIf((item) -> item.getProduct().getProductId() == productId);
		System.out.println(result);

		Cart updatedCart = cartRepository.save(cart);

		return this.mapper.map(updatedCart, CartDto.class);
	}

}
