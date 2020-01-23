package com.onlinemusicstore.app.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.service.AccessoryService;
import com.onlinemusicstore.app.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AccessoryController {
	
	@Autowired
	ProductService proService;
	
	@Autowired
	AccessoryService accessorySerivce;
	
	@RequestMapping("/accessory")
	public String accessory(Model model) {
		
	List<Product> products = proService.getAllPro();
	model.addAttribute("products", products);
	return "accessories.jsp";
	
	}
	
	@RequestMapping("/addAccessory")
	public String addAccessory(Model model,@RequestParam("productId") int productId, @RequestParam("accessory") String accessoryName,@RequestParam("accessoryPrice") int accessoryPrice) {
		
		System.out.println("the accessory name is " + accessoryName + "the product Id is" + productId);
		accessorySerivce.addAccessory(accessoryName,productId,accessoryPrice);
	return "redirect:/admin/accessory";
	
	}
	

}
