package com.onlinemusicstore.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.service.CustomerService;

@Controller
public class ProfileController {
	
	@Autowired
    private CustomerService customerService;
	
	private Path path;

	@RequestMapping("/profile/{username}")
	public String myProfle(@PathVariable("username") String username,Model model) 
	{
		System.out.println("the username is "  + username);
		Customer customerInfo = customerService.customerInfo(username);
		

		model.addAttribute("customer", customerInfo);
	
		System.out.println("the customerId is is "  );
		return "../profilePage.jsp";
	}
	
	 @RequestMapping("/changeProfile/{username}")
	 public String changeProfileGet(@PathVariable("username") String username,Model model) {
		 
		 System.out.println("the username is in edit profile  "  + username);
			Customer customerInfo = customerService.customerInfo(username);
			
			System.out.println("in edit profile the billing is " + customerInfo.getShippingAddress().getBillingAddress() + "the id is " + customerInfo.getShippingAddress().getBillingAddressId());
			model.addAttribute("customer", customerInfo);
			return "../editProfilePage.jsp";
	 }
	 

	
	 @RequestMapping("/profile/editProfile")
	 @Transactional
	 public String changeProfile(HttpServletRequest request,
			 @RequestParam("customerId") int customerId, @RequestParam("username") String username,@RequestParam(name = "profileImage") MultipartFile profileImage
			 ,@RequestParam("custmerName") String customerName, @RequestParam("customerEmail") String customerEmail, @RequestParam("customerPhone") String customerPhone 
			 ,@RequestParam("shippingAddress") String shippingAddress ,@RequestParam("billingAddress") String billingAddress ,@RequestParam("shippingId") int shippingId ,@RequestParam("billingId") int billingId ){
		 
		 
		 System.out.println("the changes are" );
		 System.out.println("the changes are " + username +"/" + "profileImage" + profileImage + "/customer name " + customerName + customerEmail  + customerPhone  + shippingAddress  + billingAddress + "///" + shippingId + " ..." + billingId);
		 
		 Customer updateCustomer = new Customer();
		 updateCustomer.setUsername(username);
		 updateCustomer.setCustomerName(customerName);
		 updateCustomer.setCustomerId(customerId);
		 updateCustomer.setCustomerEmail(customerEmail);
		 updateCustomer.setCustomerPhone(customerPhone);
		
		 ShippingAddress newShipping = new ShippingAddress();
		 newShipping.setAddress(shippingAddress);
		 newShipping.setShippingAddressId(shippingId);
		 newShipping.setBillingAddressId(billingId);
		 newShipping.setBillingAddress(billingAddress);
		 
		 updateCustomer.setShippingAddress(newShipping);
		 customerService.updateProfile(updateCustomer);
		 
		 System.out.println("the changes are " + profileImage.getSize());
		 
		 if(profileImage.getSize() != 0) {
		 String rootDirectory = request.getSession().getServletContext().getRealPath("/");
			path = Paths.get(rootDirectory + "\\resources\\images\\" + customerId + ".png");
	
			if (Files.exists(path)) 
			{
				try {
					System.out.println("file exist");
					Files.delete(path);
	
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("not deleted the path file ", e);
				}
			}
			
			path = Paths.get(rootDirectory + "\\resources\\images\\" + customerId + ".png");
			
			try 
			{
				System.out.println("in try" + path);
				profileImage.transferTo(new File(path.toString()));
				System.out.println("image are saved");
			} catch (Exception e) {
				System.out.println("in catch");
				e.printStackTrace();
				throw new RuntimeException("Product image saving failed", e);
			}
		 }
			return "redirect:/profile/" + username;
	 }
	 
	
	 
	 
}
