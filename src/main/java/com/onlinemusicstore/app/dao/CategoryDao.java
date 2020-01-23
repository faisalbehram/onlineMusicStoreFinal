package com.onlinemusicstore.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.repository.CategoryJdbcRepo;

@Repository
public class CategoryDao {
	
	@Autowired
	private CategoryJdbcRepo categoryJdbcRepo;
	
	public List<Category> allMainCategory(){
		System.out.println("the category Dao ");
		return categoryJdbcRepo.AllMainCategories();
		
	}
	public List<Category> allSubcategories(){
		System.out.println("the category Dao2 ");
		return categoryJdbcRepo.AllSubCategoires();
		
	}
	
	
	
	public Category findMainCategoryById(int categoryId) {
		
		return categoryJdbcRepo.findMainCategoryById(categoryId);
	}
	public void saveCategore(Category category) {
		categoryJdbcRepo.save(category);
		
	}
	public void saveMainCategory(String mainCategory) {
		categoryJdbcRepo.saveMainCategory(mainCategory);
	}
	public void saveSubCategory(int mainCategoryId, String subCategory) {
		categoryJdbcRepo.saveSubCategory(mainCategoryId,subCategory);
		
	}
	public List<Category> getSubCategoryByParentId(int id) {
		List<Category> subCategories = categoryJdbcRepo.getSubCategoriesByParentId(id);
		return subCategories;
	}
	public Category getCategoryById(Long id ,int productId) {
		Category category = categoryJdbcRepo.getCategoryById(id,productId);
		return category;
	}
	
	public Category getCategoryById(Long id ) {
		Category category = categoryJdbcRepo.getCategoryById(id);
		return category;
	}
	
	public Category getParentCategory(int parentId) {
		Category category = categoryJdbcRepo.getParentCategory(parentId);
		return category;
	}
	public Category findByCategoryName(String mainCategory) {
		Category category = categoryJdbcRepo.findByCategoryName(mainCategory);
		return category;
	}
	
}
