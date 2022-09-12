package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ecom.model.Product;
import com.ecom.payload.ApiResonse;
import com.ecom.payload.ProductDto;

import com.ecom.service.ProductService;



@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
	private ProductService productService;

	@PostMapping("/")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product)
	{

		ProductDto createdProduct= this.productService.createProduct(product);
		
		return new ResponseEntity<ProductDto>(createdProduct, HttpStatus.CREATED);
	}
	


	@PutMapping("/{productId}")
	public ProductDto updateProduct(@PathVariable("productId")int pid,@RequestBody ProductDto newProduct)
	{
		ProductDto updateProduct=this.productService.updateProduct(newProduct,pid);
		return updateProduct;
	}
	

	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResonse> deleteProduct(@PathVariable("productId")int pid)
	{
		this.productService.deleteProduct(pid);
		return new ResponseEntity<ApiResonse>(new ApiResonse("Product Delete successfully !!", false), HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ProductDto getProduct(@PathVariable("productId")int pid)
	{
		ProductDto product=this.productService.getProduct(pid);
		return product;
	}
	
	@GetMapping("/")
	public List<ProductDto> listAll()
	{
		List<ProductDto> product=this.productService.getAllProducts();
		return product;
		
	}
	
	

	
	
}
