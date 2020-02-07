package com.onlinemusicstore.app.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.onlinemusicstore.app.models.BillingAddress;
import com.onlinemusicstore.app.models.CaptchaResponse;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.service.CaptchaValidator;
import com.onlinemusicstore.app.service.CustomerService;
import com.onlinemusicstore.app.util.SendTheMail;

@Controller
public class RegistrationController {
	//this controller is used to register the customer with full information
	//
	
	@Autowired
    private CustomerService customerService;
	
	
	
	private Path path;
	
	   
	  private static final String GOOGLE_RECAPTCHA_VERIFY_URL ="https://www.google.com/recaptcha/api/siteverify";
	 
	  @Autowired
	  RestTemplate restTemplate;

//	@Autowired
//	private SendTheMail sendTheMail;
//	
	 @RequestMapping("/register")
	    public String registerCustomer(Model model) {

		 System.out.println("show registeration page");
	        Customer customer = new Customer();

	        ShippingAddress shippingAddress = new ShippingAddress();
	        BillingAddress billingAddress = new BillingAddress();
	        
	        billingAddress.setCustomer(customer);
	        billingAddress.setShippingAddress(shippingAddress);
	        
	      
	        
	        model.addAttribute("billingAddress", billingAddress);
	      

	        return "registerCustomerWithGoogleApi.jsp";

	    }
	 
	
	 @RequestMapping(value = "/registercustomer", method = RequestMethod.POST)
	 @Transactional
	    public String registerCustomerPost(@Valid @ModelAttribute("billingAddress") BillingAddress billingAddress, 
	    		@RequestParam("Shipping") String shippingAddres,@RequestParam("Billing") String billing,BindingResult result,
	    		@RequestParam("profileImage") MultipartFile profileImage,Model model,HttpServletRequest request
	    		,@RequestParam(name="g-recaptcha-response") String recaptchaResponse
	    		
	    		) {
		System.out.println("the responce is " + recaptchaResponse);
		  String params = "?secret=6Ld_WdYUAAAAAIdDud904TQGINF5P0IzbRAWoEAK&response="+recaptchaResponse;
		  CaptchaResponse captchaResponse = restTemplate.exchange(GOOGLE_RECAPTCHA_VERIFY_URL+params, HttpMethod.POST,  null, CaptchaResponse.class).getBody();
	
		  // any field error
		  System.out.println("posting registration");
	        if (result.hasErrors()) {
	            return "registerCustomerWithGoogleApi.jsp";
	        }
		
		
		 if(captchaResponse.getSuccess()) {
			 
			 System.out.println("the captacha  is true");
			 
			  String email = billingAddress.getCustomer().getCustomerEmail();
		        String userName = billingAddress.getCustomer().getUsername();
		       
		        // check email
		        try {
		        	if(customerService.getCustomerByEmailForId(email) != null)
			        {
		        		System.out.println("the emai alreay exits");
			        	model.addAttribute("errors", "email Already Exists");
			        	return "registerCustomerWithGoogleApi.jsp";
			        	
			        }
			   
		        }catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Email exist ", e);
					
				}
		        
		        // check UserName already Present or not
		        try {
		        	if(customerService.getCustomerByUsername(userName) != null ) {
		        		System.out.println("the username alreay exits");
			        	model.addAttribute("errors1", "userName Already Exists");
			       		return "registerCustomerWithGoogleApi.jsp";
			        	}
		        	
		        }
		        catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("UserName exist ", e);
				}
		        try {
		        	
		      
		        Customer customer  = billingAddress.getCustomer();
		        customer.setEnabled(true);
		        customerService.addCustomer(customer, shippingAddres, billing);
		   	   	String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		   		path = Paths.get(rootDirectory + "\\resources\\images\\" + customer.getCustomerId() + ".png");
		   		
		   		try 
				{
		   			System.out.println("in try" + path);
		   			profileImage.transferTo(new File(path.toString()));
	   			} catch (Exception e) {
	   				System.out.println("in catch");
		   			e.printStackTrace();
		   			throw new RuntimeException("profile image saving failed", e);
		   		}
		   	        
			   		 System.out.println("posting4 registration");
			   		return "registerCustomerSuccess.jsp";
		        }
		        catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Customer Saving failed ");
				}
		
			 
			 
		 }
		 else
		 {
			 System.out.println("the captacha  is false");
			 model.addAttribute("captchaError", "verfiy the captcha ");
			
		        return "registerCustomerWithGoogleApi.jsp";
		        
		 }

	   	 
	   	 
	   	 
	    }



}
