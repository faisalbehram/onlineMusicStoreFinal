package com.onlinemusicstore.app.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.PricesWithDiscount;
import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.service.CategoryService;
import com.onlinemusicstore.app.service.Price2Service;
import com.onlinemusicstore.app.service.ProductService;

@Controller
@RequestMapping("/admin")
public class PriceController {
	
	@Autowired
	private Price2Service price2Service;
	
	@Autowired
	private ProductService proService;
	
	@Autowired
	private CategoryService categoryService;


	/*
		this is pricing controller from admin which is controlled by admin
		
	*/	
	@RequestMapping("/pricing")
	public String pricing(Model model) {
		
		List<Product> products = proService.getAllPro();
		List<Price2> discountPrices = price2Service.findAllDiscountPrice();
	
		List<Price2> basePrices = price2Service.findAllBasicPrice();

		List<PricesWithDiscount> discounts  = price2Service.findDiscounts3(discountPrices);
	
		Optional<Price2> genericDiscount = price2Service.findGenericAllDiscount();
		
		List<Category> mainCategories = categoryService.allMainCategories();
		List<Category> subCategories = categoryService.allSubcategoies();
		 
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("mainCategories", mainCategories);
		
		model.addAttribute("products", products);
		model.addAttribute("discountPrices", discountPrices);
		model.addAttribute("listOfBasePrices", basePrices);
		model.addAttribute("discounts", discounts);
		model.addAttribute("genericDiscount", genericDiscount);
		
		return "priceing.jsp";
	}
	
	@RequestMapping(value = "/pricing",method = RequestMethod.POST)
	public String savePrcing(Model theModel,@RequestParam(value = "percentageDiscount", required = false, defaultValue = "0")double percentageDiscount
			,@RequestParam("product") Product product,@RequestParam("basePrice") double basePrice) {
		
		Price2 price2 = new Price2();

		// this for those product who havent any price yet so if have to add price first
		// check the product is present or not in pricing table
		
		List<Price2> priceId = price2Service.findbyProductId(product.getProductId());
		
		
		System.out.println("price is  " + priceId);
		// if present then update the price with the discount
		// if not create with new id
		
		if (!priceId.isEmpty()) {
			System.out.println("in of if condition and the id is " + priceId );
			price2.setId(product.getPrice2().get(1).getId());
		}
		System.out.println("out of if condition");
		price2.setProduct(product);
		System.out.println( "in percentageDiscount " + percentageDiscount + "/// the id is" + product);
		
		price2.setPercentageDiscount(percentageDiscount);
		
		price2.setProduct(product);
		
		price2.setPercentageDiscount(percentageDiscount);
		
		price2.setPrice(basePrice);
		
		price2Service.saveThePrice(price2);
		
		return "admin.jsp";
	}
	
	
	@RequestMapping(value = "/disableDiscount" )
	public String disableDiscount(@RequestParam(value = "priceId" ,required = false) int priceId,@RequestParam( value = "priceId" ,required = false ) String productId) {
		System.out.println("in disabble discount is ");
		if(!productId.isEmpty()) {
		
		Optional<Price2> price2 = price2Service.findById(priceId);
		System.out.println("the discount is  " + price2 + "the all product is " + price2.get().getId());
		price2Service.disableThePrice(price2.get());
			
		}
		else {
			System.out.println("in else the string is all");
			
		}
		

		return "redirect:/admin/pricing";
	}

	
	@RequestMapping("/updateDiscount")
	public String UpdateDiscount(@RequestParam("discount" ) double discount ,
								@RequestParam("allProduct") int productId) {		
		String page = price2Service.updateDiscount(discount,productId);	
		return page;
		}
	
	@RequestMapping("/updateTax")
	public String UpdateTax(@RequestParam("tax" ) double tax) {		
		String page = price2Service.updateTax(tax);	
		return page;
		}
	
	
	@RequestMapping("/updateCategoryDiscount")
	public String UpdateCategoryDiscount(@RequestParam("discount" ) double discount,@RequestParam("allCategories") Long categoryId) {
	
				System.out.println("in updating category discount");
				Category category = categoryService.getCategoryById(categoryId);
				System.out.println("when All is not and cateogy Id is" + categoryId + "the category detail is "  +category.getId());
				
				System.out.println("checking category if present is");
				Price2 findCategoryDiscount =price2Service.findCategoryDiscount(categoryId);
				
				System.out.println("checking category if present is" + findCategoryDiscount);
				
				if(findCategoryDiscount == null) {
					System.out.println("the category is null");
					System.out.println("saving new categorydiscount");
					price2Service.saveCategoryDiscount(discount,category);
					
				}
				else {
					System.out.println("the category is not null");
					System.out.println("the all product updateing discount is");
					
					int genericId = findCategoryDiscount.getId();
					System.out.println("Update  generic discount is the Id is" + findCategoryDiscount.getId() );
					price2Service.updatingCategoryDiscount(discount, genericId,category);
				}
					
	return "admin.jsp";	
}
}

