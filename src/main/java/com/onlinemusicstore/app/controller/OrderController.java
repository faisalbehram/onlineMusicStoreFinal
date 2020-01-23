package com.onlinemusicstore.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinemusicstore.app.dao.ShippingDao;
import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.service.CartItemService;
import com.onlinemusicstore.app.service.CartService;
import com.onlinemusicstore.app.service.OrderService;

/**
 * Created by Le on 1/25/2016.
 */

@Controller
public class OrderController {

	//  to check out the cart this controller is used which update the order table
	// order are saved relative to there Id
	
    @Autowired
    private CartService cartService;
        
    @Autowired
    private CartItemService cartItemService;
    
    @Autowired
    ShippingDao shippingDao;
    
    @Autowired
    private OrderService orderService;
    // check out for the orders 
    
    @RequestMapping("/checkout/{cartId}")
    public String showCustomerInfo(@PathVariable("cartId") int cartId,Model model) {
    	
    	System.out.println("in order controler");
        List<Cart> cart=cartService.getCartbyIdForCheckOut(cartId);
      
       
        model.addAttribute("cart", cart.get(0));
    
        
        return "collectCustomerInfo.jsp";
    }
    
   
    // this will show a recipe of the order where all grand total user info shiping info and product info are there
    @RequestMapping("/checkout/savingOrder/{cartId}")
    public String savingOrder(@PathVariable("cartId" )int cartId , Model model,@RequestParam("Shipping") String shipping,@RequestParam("Billing") String billing) {
    	System.out.println("in recipe the shipping" + shipping +" the billing is " + billing);
    	
    	// check for changes
    	ShippingAddress checkShipping = orderService.checkShipping(shipping,cartId);
    	ShippingAddress checkBilling = orderService.checkBilling(billing,cartId);
    	
    	List<Cart> cart=cartService.getCartbyIdForCheckOut(cartId);
    	//get the cart info
    	
    	
    	System.out.println("in GETCART BY ID IS " + cart);
 
    	// get all items on cart
    	
	   	 List<CartItem> cartItems = cartItemService.showCart(cartId);
	 	System.out.println("in all carts items " + cartItems);
	 	
	 	model.addAttribute("newShippingAddress", checkShipping);
	 	model.addAttribute("newBillingAddress", checkBilling);
	 	model.addAttribute("cart", cart.get(0));
	   	 model.addAttribute("cartItems", cartItems);
    	return "../orderConfirmation.jsp";
    }
    
    @Transactional
    @RequestMapping("/checkout/savingConfirmed/{cartId}")
    public String savingConfirmed(@PathVariable("cartId") int cartId,Model model, @RequestParam("shipping") String shipping,@RequestParam("billing") String billing ) {
    	System.out.println("in order controler" + shipping + "the billing is  " + billing);
    	
    	orderService.orderConfirmation(cartId,shipping,billing);
  
        return "../thankCustomer.jsp";
    }
  

	@RequestMapping("/checkout/checkOutCancelled")
    public String cancelled() {
    	return "checkOutCancelled.jsp";
    }
    
}
