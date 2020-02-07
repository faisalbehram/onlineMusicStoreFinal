package com.onlinemusicstore.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Le on 1/11/2016.
 */


public class CartItem implements Serializable{

    public CartItem(Product product) {
		super();
		this.product = product;
	}

	public CartItem() {
		
	}

	private static final long serialVersionUID = -904360230041854157L;

	@Id
    private int cartItemId;

    @ManyToOne
    @JoinColumn(name = "cartId")
    @JsonIgnore
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "accessoryId")
    private Accessories accessories;
    
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "cartitem_accessory",joinColumns = @JoinColumn(name= "parent_cartitem_id"),inverseJoinColumns = @JoinColumn(name = "child_accessory_id"))
	private Set<Accessories> accessory =  new HashSet<>();
    
       
    public Set<Accessories> getAccessory() {
		return accessory;
	}

	public void setAccessory(Set<Accessories> accessory) {
		this.accessory = accessory;
	}

	
	public Accessories getAccessories() {
		return accessories;
	}

 
    
   

	public void setAccessories(Accessories accessories) {
		this.accessories = accessories;
	}

	private int quantity;
    
    private double totalPrice;

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
