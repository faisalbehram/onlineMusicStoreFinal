package com.onlinemusicstore.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.repository.ProductJdbcRepo;

@Service
public class ProductService {

	
	@Autowired
	private ProductJdbcRepo jdbcRepo;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired Price2Service price2Service;
	
	
	public List<Product> getAllPro(){
		System.out.println("getallpro");
		return jdbcRepo.allProducts();
	}
	
	public Product saveProduct(Product product, int categoryId) {
		System.out.println("saving");
		
		Category category = categoryService.findMainCateguryById(categoryId);
		System.out.println("the category is "  +  category + "and the id is  " + categoryId);
		product.setCategory(category);
		Product productId = jdbcRepo.save(product);
		System.out.println("saved");
		return productId;
	}
	
	public Product getProById(int productId) {
		
		System.out.println("in productById is id + " + productId );
	Product pro = jdbcRepo.getProductById(productId);
	System.out.println("in productById is + " + pro  + "the tax is " + pro.getPrice2().get(0).getPercentageDiscount());
	
	return pro;
	}
	
	public List<Product> getProductByName(String productName) {
		
		System.out.println("in productById is id + " + productName );
	List<Product> pro = jdbcRepo.findProductByName(productName);
	System.out.println("in productById is + " + pro );
	return pro;
	}
	
	public void deleteProduct(String id) {
		jdbcRepo.deleteProduct(id);
	}

	public void editProduct(Product product) {
		System.out.println("edit is saving");
		jdbcRepo.save(product);
		System.out.println("edit is saved");
		
	}

	
}
