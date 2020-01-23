package com.onlinemusicstore.app.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


public class Accessories  {



	@Id
	private int accessoryId;
	
	private String accessoryName;
	
	@ManyToMany(mappedBy = "accessories",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();
	
	
    @OneToMany(mappedBy = "accessories", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;
   
    
    
    @ManyToMany(mappedBy = "accessory",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<CartItem> cartItem = new HashSet<>();
    
    @OneToMany(targetEntity =Price2.class,mappedBy = "accessories" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price2> price2;
    
    
    
    public List<Price2> getPrice2() {
		return price2;
	}

	public void setPrice2(List<Price2> price2) {
		this.price2 = price2;
	}

	int parentId;
    int childId;
    

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public Set<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Set<CartItem> cartItem) {
		this.cartItem = cartItem;
	}

	public Accessories(int accessoryId, String accessoryName, Set<Product> products) {
		super();
		this.accessoryId = accessoryId;
		this.accessoryName = accessoryName;
		this.products = products;
		
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Accessories() {
		// TODO Auto-generated constructor stub
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public int getAccessoryId() {
		return accessoryId;
	}

	public void setAccessoryId(int accessoryId) {
		this.accessoryId = accessoryId;
	}

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	
	
	
	

}
