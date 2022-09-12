package com.ecom.service;

import java.util.List;

import com.ecom.model.Product;
import com.ecom.payload.ProductDto;


public interface ProductService {
	public ProductDto createProduct(ProductDto product);
	public ProductDto updateProduct(ProductDto newProduct,int productId);
	public void deleteProduct(int productId);
	public ProductDto getProduct(int productId);
	public List<ProductDto> getAllProducts();
	
}
