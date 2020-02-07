package com.onlinemusicstore.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.dao.CategoryDao;
import com.onlinemusicstore.app.models.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	public List<Category> allMainCategories(){
		return categoryDao.allMainCategory();
		
	}
	
	
	public List<Category> allSubcategoies(){
		return categoryDao.allSubcategories();
		
	}
	
	public Category findMainCateguryById(int categoryId) {
		return  categoryDao.findMainCategoryById(categoryId);
	}

	


	public void saveMainCategory(String mainCategory) {
		categoryDao.saveMainCategory(mainCategory);
		
	}


	public void saveSubCategory(int mainCategoryId, String subCategory) {
		categoryDao.saveSubCategory(mainCategoryId,subCategory);
		
	}


	public List<Category> getSubCategoryByParentId(int id) {
		List<Category> subCategories = categoryDao.getSubCategoryByParentId(id);
		return subCategories;
	}


	public Category getCategoryById(Long id,int productId) {
		Category category  = categoryDao.getCategoryById(id,productId);
		return category;
	}

	public Category getCategoryById(Long id) {
		Category category  = categoryDao.getCategoryById(id);
		return category;
	}

	public Category getParentCategory(int parentId) {
		Category category  = categoryDao.getParentCategory(parentId);
		return category;
	}


	public Category findByCategoryName(String mainCategory) {
		Category category = categoryDao.findByCategoryName(mainCategory);
		return category;
	}



}
