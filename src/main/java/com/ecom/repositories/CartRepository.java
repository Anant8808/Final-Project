package com.ecom.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Cart;
import com.ecom.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByUser(User user);

}