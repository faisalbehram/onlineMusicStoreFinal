package com.onlinemusicstore.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.PricesWithDiscount;
import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.service.AccessoryService;
import com.onlinemusicstore.app.service.CategoryService;
import com.onlinemusicstore.app.service.Price2Service;
import com.onlinemusicstore.app.service.ProductService;

@Controller
public class ProductController {
	
	// this is product controller which is used to update the product and product list for user
	// also to search the cart
	
	@Autowired
	private ProductService proService;	
	
	@Autowired
	private Price2Service price2Service;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AccessoryService accessoriesService;
	
	// show product Inventry page
		@RequestMapping("/productList")
		public String productinventory(Model model) {
			
			List<Product> products = proService.getAllPro();
			//	List<Price2> getAllPrices = price2Service.pricesList();
				
				model.addAttribute("products", products);
				
				System.out.println("the product list is " + products);
				System.out.println("the pricewith discount is " + products.size());
			
			
			System.out.println("now to display the page");
			
			// all basic prices
			List<Price2> allBasicPrice = price2Service.findAllBasicPrice();
			
			// all discount prices mean that it will fetch all that product which have discounts
			List<Price2> allDiscountPrice = price2Service.findAllDiscountPrice();	
			
			// findDiscounts will done all the discounts function for thats products which id are prsensted related to discount type
			List<PricesWithDiscount> discounts  = price2Service.findDiscounts3(allDiscountPrice);
			
			List<Category> mainCategories = categoryService.allMainCategories();
			 List<Category> subCategories = categoryService.allSubcategoies();
			 
		//	 List<Price2> allCategoryDiscounts = price2Service.findAllCategoryPrice();
			
			 System.out.println("the category is " + discounts.get(1).getCategoryId().getId());
			 
			List<PricesWithDiscount> categoryDiscount =  price2Service.findDiscountsWithCategory();
			 
			 
			System.out.println("the size of allBasicPrice + " + allBasicPrice.size());
			System.out.println("the size of allDiscountPrices + " + allDiscountPrice.size());
			System.out.println("the size of categories are + " + mainCategories.get(0).getId());

			model.addAttribute("subCategories", subCategories);
			model.addAttribute("mainCategories", mainCategories);
			model.addAttribute("categoryDiscounts", categoryDiscount);
			
			model.addAttribute("basicPrice", allBasicPrice);
			model.addAttribute("discountPrice", allDiscountPrice);
			model.addAttribute("discounts", discounts);
		
			
			return "productList.jsp";
		}
	
	
	
	
	
	@RequestMapping("viewProduct/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) throws IOException{
		
		
        Product product = proService.getProById(productId);			// geting product detail with also taxible sign if present then tax will be applied
        
       
        System.out.println("the parent id is " + product.getCategory().getId());
        
        Category mainCategories = categoryService.getCategoryById(product.getCategory().getId(),productId); // find category
        
        // now find the discount
        PricesWithDiscount discountPrice = price2Service.getProductDiscount(productId,mainCategories.getId(),mainCategories.getParentId());
        
        
        // now add the tax
        Price2 withTaxprice = price2Service.addTax(discountPrice.getDiscountPrice(),product.getPrice2().get(0).getPercentageDiscount());
       
        List<Price2> listOfPrice = new ArrayList<Price2>();
        listOfPrice.add(withTaxprice);
        product.setPrice2(listOfPrice);
        
        
        
        // if there is sub categories
        if(mainCategories.getParentId() != 0) {
        	  System.out.println("theere is parent category ");
        	Category subCategories = categoryService.getParentCategory(mainCategories.getParentId());
        	 model.addAttribute("subCategories", subCategories);
        }
		
		
		
		List<Accessories> accessories = accessoriesService.accessoriesByproductId(productId);
		
		model.addAttribute("pricesWithDiscount", discountPrice);
		model.addAttribute("mainCategories", mainCategories);
		model.addAttribute(product);
		model.addAttribute("accessories", accessories);
        return "../viewProduct2.jsp";
    }
	
	
	@RequestMapping("/searchproduct")
	public String product(Model model, @RequestParam("productName") String name) {
		
		System.out.println("in search the product name is " + name);
		model.addAttribute("products", proService.getProductByName(name));
		
		
		// all basic prices
		List<Price2> allBasicPrice = price2Service.findAllBasicPrice();
		
		// all discount prices mean that it will fetch all that product which have discounts
		List<Price2> allDiscountPrice = price2Service.findAllDiscountPrice();	
		
		// findDiscounts will done all the discounts function for thats products which id are prsensted related to discount type
		List<PricesWithDiscount> discounts  = price2Service.findDiscounts3(allDiscountPrice);
		
		List<Category> mainCategories = categoryService.allMainCategories();
		
		model.addAttribute("mainCategories", mainCategories);
		model.addAttribute("basicPrice", allBasicPrice);
		model.addAttribute("discountPrice", allDiscountPrice);
		model.addAttribute("discounts", discounts);
		
		System.out.println("in search the product name is " + name);
		return "productList.jsp";
	}
	
	
	@RequestMapping("/searchProductByCategory")
	public String searchProduct(Model model, @RequestParam("MainCategory") int id) {
		System.out.println("in search the product name is " + id);
		List<Category> categories = categoryService.getSubCategoryByParentId(id);
		
		model.addAttribute("categories", categories);
		System.out.println("in the cateogires are name is " + categories.size());
		
		
		// all basic prices
		List<Price2> allBasicPrice = price2Service.findAllBasicPrice();
		
		// all discount prices mean that it will fetch all that product which have discounts
		List<Price2> allDiscountPrice = price2Service.findAllDiscountPrice();	
		
		
		// findDiscounts will done all the discounts function for thats products which id are prsensted related to discount type
		List<PricesWithDiscount> discounts  = price2Service.findDiscounts3(allDiscountPrice);
		
		 List<Category> subCategories = categoryService.allSubcategoies();
		 List<Category> mainCategories = categoryService.allMainCategories();
			
		model.addAttribute("mainCategories", mainCategories);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("basicPrice", allBasicPrice);
		model.addAttribute("discountPrice", allDiscountPrice);
		model.addAttribute("discounts", discounts);
	
		
		
		return "categoryList.jsp";
	}

}
