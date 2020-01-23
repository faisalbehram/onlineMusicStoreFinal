package com.onlinemusicstore.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.service.AccessoryService;
import com.onlinemusicstore.app.service.CartItemService;
import com.onlinemusicstore.app.service.CategoryService;

@Controller
public class HomeController implements ErrorController {
	
	private static final String path = "/error";
	
	@Autowired
	private CartItemService cartItemService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AccessoryService accessoriesService;
	
	
	
	@RequestMapping("/")
	public String home(Model model) 
	{
		List<Category> mainCategorie = categoryService.allMainCategories();
		model.addAttribute("mainCategories", mainCategorie);
		return "home.jsp";
	}
	
	// show the cart of customer or admin.. but when login .. 
	@RequestMapping("/showcart/{cartId}")
	public String viewCart(@PathVariable (value = "cartId") int cartId, Model model)
	{
		System.out.println("in view cartt cart id is" + cartId );
 	
		List<CartItem> cartItems = cartItemService.showCart(cartId);
		
		List<Accessories> accessories = accessoriesService.getAccessoryByCartId(cartId);
		model.addAttribute("accessories", accessories);
	
		model.addAttribute("cartId", cartId );
		model.addAttribute("items",cartItems);
		return "../cart.jsp";	
	}
	
	// show the cart of customer or admin.. but when login .. 
		@RequestMapping("/showcart")
		public String viewCartWhenNoItem(Model model)
		{
			System.out.println("in view cartt cart id is when no cart"  );
	 	
			model.addAttribute("msg", "there is not item in cart");
			return "cartnoitem.jsp";	
		}
	
	@RequestMapping("/about")
	public String about() {
		return "about.jsp";
	}

	@Override
	public String getErrorPath() {
		
		return path;
	}
	
	@RequestMapping("/error")
	public String errorMessage() {
		return "no mapping available";
	}
	
	
	
	
}
