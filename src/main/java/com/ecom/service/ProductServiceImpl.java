package com.ecom.service;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.model.Product;
import com.ecom.payload.ProductDto;

import com.ecom.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	@Override
	public ProductDto createProduct(ProductDto productDto) {
		                
		  Product newProduct= this.mapper.map(productDto, Product.class);
		
	        Product savedProduct=this.productRepository.save(newProduct);
		return this.mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto newProduct, int productId) {
		
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product with " + productId + " not found on server !!"));
		
		product.setProductName(newProduct.getProductName());
		product.setProductDesc(newProduct.getProductDesc());
		product.setProductPrice(newProduct.getProductPrice());
		product.setLive(newProduct.isLive());
		product.setStock(newProduct.isStock());
		product.setImageName(newProduct.getImageName());
		Product updatedProduct = this.productRepository.save(product);
		return this.mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void deleteProduct(int productId) {
		
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product with " + productId + " not found on server !!"));
		this.productRepository.delete(product);
	}

	

	

	@Override
	public ProductDto getProduct(int productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product with " + productId + " not found on server !!"));
		
		return this.mapper.map(product, ProductDto.class);
	}
	

	@Override
	public List<ProductDto> getAllProducts() {
		
		  List<Product> all=this.productRepository.findAll();
		List<ProductDto> dtos =all.stream().map(product->this.mapper.map(product,ProductDto.class)).collect(Collectors.toList());
		
		return dtos;
		  
	}

	
	}


