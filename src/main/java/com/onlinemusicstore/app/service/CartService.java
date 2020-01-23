package com.onlinemusicstore.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.dao.CartDao;
import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.models.Cart;

@Service
public class CartService {
	
	@Autowired
	private CartDao cartDao;
	
	public Cart getCartbyId(int cartId) {
		return cartDao.getCartById(cartId);
	}

	public List<Cart> getCartbyIdForCheckOut(int cartId) {
		return cartDao.getCartByIdForCHeckout(cartId);
	}

	public Cart saveCart(Cart cart) {
		return cartDao.saveCart(cart);
	}



	
	public void updateTheCart(Cart cart,Double totalPrice) {
			System.out.println("in CartService Update " + cart.getCartId() +  " and the price is " + totalPrice);
			 cartDao.UpdateCart(cart,totalPrice);
			
	}

	public void updateTheCartProcess(Cart cart) {
		cartDao.updateTheCartProcess(cart);
	}

	public void deleteTheCart(Cart cart) {
		cartDao.deleteTheCart(cart);
		
	}

}
