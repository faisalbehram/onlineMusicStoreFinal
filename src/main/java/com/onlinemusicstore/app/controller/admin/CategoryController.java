package com.onlinemusicstore.app.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/addCategory")
	public String addCategory(Model model) {
		List<Category> mainCategories = categoryService.allMainCategories();
		List<Category> subCategories = categoryService.allSubcategoies();
		
		
		System.out.println("the subcateguies is  "  + subCategories.get(0).getId() + " the parent id is  " + subCategories.get(0).getParentId());
		model.addAttribute("mainCategories", mainCategories);
		model.addAttribute("subCategories",subCategories );
		return "category.jsp";
	}
	
	@RequestMapping("/addMainCategory")
	public String addMainCategory(@RequestParam("mainCategory") String mainCategory,Model model) {
		Category category = categoryService.findByCategoryName(mainCategory);
		System.out.println("category Name is "  + category);
		if(category ==null) {
			System.out.println("the main category is " + mainCategory);
			categoryService.saveMainCategory(mainCategory);
			model.addAttribute("categoryMsg", "Category Added");
			return "admin.jsp";
		}
		else {
			model.addAttribute("categoryMsg", "Category Name Already Found");
			return "admin.jsp";
		}
		
		
	}
	@RequestMapping("/addSubCategory")
	public String addSubCategory(@RequestParam("MainCategory") int mainCategoryId , @RequestParam("subCategory") String subCategory,Model model) {
		System.out.println("the main category is " + mainCategoryId);
		System.out.println("the sub category is " + subCategory);
		Category category = categoryService.findByCategoryName(subCategory);
		if(category == null) {
			categoryService.saveSubCategory(mainCategoryId,subCategory);
			model.addAttribute("categoryMsg", "subCategory Added");
			return "admin.jsp";
		}else {
			model.addAttribute("categoryMsg", "Sub Category Name Already Found");
			return "admin.jsp";
		}
		
	}
	
	

}
