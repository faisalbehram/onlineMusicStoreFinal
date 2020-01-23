package com.onlinemusicstore.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.PricesWithDiscount;
import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.service.CartItemService;
import com.onlinemusicstore.app.service.CartService;
import com.onlinemusicstore.app.service.CategoryService;
import com.onlinemusicstore.app.service.CustomerService;
import com.onlinemusicstore.app.service.Price2Service;
import com.onlinemusicstore.app.service.ProductService;

/**
 * Created by Le on 1/25/2016.
 */

@Controller
@RequestMapping("/cart")
public class CartResources {

	// this is main controller where you have to add items to cart and this controller is used as resource as a service
	
	// can delete items from cart
	// can clear full cart

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private Price2Service price2Service;
	
	@Autowired
	private CategoryService categoryService;
	
	
	// to get the cart 
	
	@RequestMapping(name = "/{cartId}", method = RequestMethod.POST)
	public @ResponseBody Cart getCartById(@PathVariable(value = "cartId") int cartId) {
		return cartService.getCartbyId(cartId);
	}

	// add the items to the cart also it show that if user are new creat a new cart id
	

	@RequestMapping(value = "/add/{email}/{productId}")
	public String addItem(@RequestParam(value = "accessoryId",required = false) List<Integer> accessoryId,@PathVariable("email") String email,
			@PathVariable("productId") int productId ) {
		

		System.out.println("in additem class the accessory id is" + accessoryId);
		double	accessoryPrices = 0; 
		if(accessoryId != null) {
			accessoryPrices = price2Service.getAccessoryPrice(accessoryId);
			System.out.println("the accessory price is" + accessoryPrices);
			
		}
		

		Customer customer = customerService.getCustomerByEmail(email);
		Customer getCustomerId = customerService.getCustomerByEmailForId(email);
		
		// if there is already cart so it will fetch the previce cart other vise it will be create new cart and enter the product
		Cart cart = new Cart();
		if(customer == null ) {
			cart.setCustomer(getCustomerId);
			cart = cartService.saveCart(cart);
			System.out.println("the new cart are saved" + cart.getCartId());
			System.out.println("the new cart are saved1" );
		}
		else {
			cart = customer.getCart();
		}
		

		Product product = productService.getProById(productId);
		Category mainCategories = categoryService.getCategoryById(product.getCategory().getId(),productId);
		
		System.out.println("product id  is  +++ " + product + "the cartitem is ");
		
		

		List<CartItem> cartItems = cartItemService.showCart(cart.getCartId());
		System.out.println("the cartItems size is" 
				+ cartItems.size());
		// if already there is items in cart with same product id then just increase the quantity and grand total
		
		for (int i = 0; i < cartItems.size(); i++) 
		{
			System.out.println("in for in" + cartItems.get(i).getProduct().getProductId());
			if (product.getProductId() == cartItems.get(i).getProduct().getProductId()) 
			{
				System.out.println("in for in and in iff" );
				
				CartItem cartItem = cartItems.get(i);
				System.out.println("the cartItem id is " +cartItem.getCartItemId()  );
				
				System.out.println("the cartItem id is 1 " +cartItem.getCartItemId());
				cartItem.setQuantity(cartItem.getQuantity() + 1);
				cartItem.setCart(cart);
				// this class is not in database this is just to hold the data  (PriceWithDiscount)
				PricesWithDiscount discountPrice  = price2Service.getProductDiscount(productId,mainCategories.getId(),mainCategories.getParentId());
				
				Price2 withTaxPrice = price2Service.addTax(discountPrice.getDiscountPrice(),product.getPrice2().get(0).getPercentageDiscount());
				
				
				//if the discount is zero for a prpduct the apply baseprice in total price
				if(discountPrice.getDiscountPrice() == 0) {
					
					cartItem.setTotalPrice(withTaxPrice.getPrice() * cartItem.getQuantity() + accessoryPrices);
				
					//cartItemService.addCartItem(cartItem);
					cartItemService.addCartItemWithAccessoryId(cartItem, accessoryId);
					
					cartService.updateTheCart(cart, cartItem.getTotalPrice());
					
					
					return "redirect:/showcart/" + cartItem.getCart().getCartId();
					
				}
				else 
				{
					cartItem.setTotalPrice(withTaxPrice.getPrice() * cartItem.getQuantity()   + accessoryPrices);
					System.out.println("the discount price is total " + cartItem.getTotalPrice() );
				
					
					//cartItemService.addCartItem(cartItem);
					cartItemService.addCartItemWithAccessoryId(cartItem, accessoryId);

					cartService.updateTheCart(cart, discountPrice.getDiscountPrice());
					
					System.out.println("to shwo the page");
					return "redirect:/showcart/" + cartItem.getCart().getCartId();
					
				}
			}
		}
		//if there is not item in cart item table then to create a new one
		
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(1);
		PricesWithDiscount discountPrice  = price2Service.getProductDiscount(productId,mainCategories.getId(),mainCategories.getParentId());
		
		Price2 withTaxPrice = price2Service.addTax(discountPrice.getDiscountPrice(),product.getPrice2().get(0).getPercentageDiscount());

		
		if(discountPrice.equals(null) ) {
			cartItem.setTotalPrice(withTaxPrice.getPrice() * cartItem.getQuantity()  + accessoryPrices);
			cartItem.setCart(cart);

			System.out.println("Saving the cart items  ");
			//cartItemService.addCartItem(cartItem);
			cartItemService.addCartItemWithAccessoryId(cartItem, accessoryId);
			
			System.out.println("updateing the cart  ");
			cartService.updateTheCart(cart, cartItem.getTotalPrice());
		}
		else {
			cartItem.setTotalPrice(withTaxPrice.getPrice() * cartItem.getQuantity() + accessoryPrices);
			cartItem.setCart(cart);

			System.out.println("Saving the cart items   + the cart item quantity is "  + cartItem.getQuantity());
			//cartItemService.addCartItem(cartItem);
			cartItemService.addCartItemWithAccessoryId(cartItem, accessoryId);
			System.out.println("updateing the cart  the total discount price is " +  discountPrice.getDiscountPrice() );
			cartService.updateTheCart(cart, withTaxPrice.getPrice());
		}
		
		System.out.println("show th cart again redirect");

		return "redirect:/showcart/" + cartItem.getCart().getCartId();
	}
	

	// remove the cartitem from cart
	@RequestMapping(value = "/remove/{cartItemId}")
	public String removeItem(@PathVariable(value = "cartItemId") int cartItemId) {
     	System.out.println("removing cart to the system" + cartItemId);

		CartItem cartItem = cartItemService.getItemById(cartItemId);
		int id = cartItem.getCart().getCartId();
		// cartItem= cartItemService.getItemById(cartItemId);

		System.out.println("removing cart to the system getthe product"+ id );

		cartItemService.removeCartItem2(cartItemId);
		System.out.println("removing cart to the system done");

		return "redirect:/showcart/" + id;
	}
	
	// clear the whole cart
	@Transactional
	@RequestMapping(value = "/clear/{cartId}")
	public String clearCart(@PathVariable(value = "cartId") int cartId) {

		System.out.println("in clearcart the cartId is  " + cartId);

		Cart cart = cartService.getCartbyId(cartId);

		System.out.println("in clearcart the cart is after fetching " + cart.getCartId());

		cartItemService.removeAllCartItems(cart.getCartId());
		cartService.deleteTheCart(cart);

		return "redirect:/showcart";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload.")
	public void handleClientErrors(Exception e) {
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error.")
	public void handleServerErrors(Exception e) {
	}
}
