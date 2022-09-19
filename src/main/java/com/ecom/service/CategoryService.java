package com.ecom.service;

import java.util.List;

import com.ecom.payload.CategoryDto;

public interface CategoryService {
     
	
	CategoryDto create(CategoryDto dto);

	// update
	CategoryDto update(CategoryDto dto, int categoryId);

	// delete

	void delete(int categoryId);

	//get single

	CategoryDto get(int categoryId);

	//get all
	List<CategoryDto> get();

}
