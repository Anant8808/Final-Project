package com.ecom.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Order;
import com.ecom.model.User;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByUser(User user);
}
