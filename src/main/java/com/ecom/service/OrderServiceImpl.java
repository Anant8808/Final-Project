package com.ecom.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;

import com.ecom.payload.ProductDto;

import com.ecom.repositories.CartRepository;
import com.ecom.repositories.OrderRepository;
import com.ecom.repositories.UserRepository;
import com.ecom.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public OrderDto createOrder(OrderRequest request, String username) {
		
		int cartId = request.getCartId();
		String address = request.getAddress();
		
		User user = this.userRepository.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
		
		Cart cart = this.cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);
		
		Set<CartItem> items = cart.getItems();
		
		Order order = new Order();
		
		AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
		
		Set<OrderItem> orderItems = items.stream().map((cartItem)-> {
			
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalProductPrice(cartItem.getTotalProductPrice());
			
			orderItem.setOrder(order);
			totalOrderPrice.set(totalOrderPrice.get()+ orderItem.getTotalProductPrice());
			
			
			
			
			return orderItem;
			
		}).collect(Collectors.toSet());
		
		order.setItems(orderItems);
		order.setBillingAddress(address);
		order.setPaymentStatus("NOT PAID");
		order.setOrderAmount(totalOrderPrice.get());
		order.setOrderCreated(new Date());
		order.setOrderDelivered(null);
		order.setOrderStatus("CREATED");
		order.setUser(user);
		
		Order savedOder = this.orderRepository.save(order);
		
		cart.getItems().clear();
		this.cartRepository.save(cart);
		
		return this.modelMapper.map(savedOder, OrderDto.class);
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, int orderId) {

		Order order = this.orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		String orderStatus = orderDto.getOrderStatus();
		String paymentStatus = orderDto.getPaymentStatus();
		Date orderDelivered = orderDto.getOrderDelivered();
		order.setOrderStatus(orderStatus);
		order.setPaymentStatus(paymentStatus);
		if(order.getOrderStatus().equalsIgnoreCase("Done")) 
			order.setOrderDelivered(new Date());
		else
			order.setOrderDelivered(null);
		
		Order updatedOrder = this.orderRepository.save(order);
		
		return this.modelMapper.map(updatedOrder, OrderDto.class);
	}

	@Override
	public void deleteOrder(int orderId) {

		Order order = this.orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		this.orderRepository.delete(order);
		
	}


	@Override
	public OrderDto get(int orderId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		
		return this.modelMapper.map(order, OrderDto.class);
	}

	@Override
	public List<OrderDto> getAll() {
		
		 List<Order> all=this.orderRepository.findAll();
		 List<OrderDto> dtos= all.stream().map((order)->this.modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());
			
			return dtos;
			  
	}

}
