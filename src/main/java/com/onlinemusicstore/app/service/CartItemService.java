package com.onlinemusicstore.app.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.dao.CartItemDao;
import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;

/**
 * Created by Le on 1/25/2016.
 */

@Service
public class CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private CartService cartService;
    
    public void addCartItem(CartItem cartItem) 
    {
        cartItemDao.addCartItem(cartItem);
    }
    
    public void addCartItemWithAccessoryId(CartItem cartItem,List<Integer> accessoryId) 
    {
    	
        int getcartItemId = cartItemDao.addCartItem(cartItem);
        
        if(accessoryId != null) {
        	
        	for(int i = 0 ; i<accessoryId.size(); i++){
            	
            	int getAccessoryId = accessoryId.get(i);
            	int id = cartItemDao.addCartItemWithAccessory(cartItem, getAccessoryId);
            	System.out.println("in for loof the parent id is" + getcartItemId + "and the child id is" + id);
            	cartItemDao.saveJoinCartItemAccessory(getcartItemId, id);	
            }
        	
        }
        
        
    }

    public List<CartItem> cartItemWithAccessories(){
		return null;
    	
    }
   
    
    
    public void removeCartItem2(int cartItem) {
    	System.out.println("the cartitem id is in service " + cartItem);
        cartItemDao.removeCartItembyId(cartItem);;
    }
    

    public void removeAllCartItems(int cartId){
    	cartItemDao.removeByCartId(cartId);
    }

    
    public List<CartItem> findAllItems(){
    	return cartItemDao.showAll();
    }
   
    public List<CartItem> showCart(int cartId){
    	Cart cart = cartService.getCartbyId(cartId);
    	
    	return cartItemDao.showMyCart(cartId, cart);
    }

	public CartItem getItemById(int cartItemId) {
		return cartItemDao.getItemById(cartItemId);
	}
    
}
