package com.onlinemusicstore.app.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Le on 1/24/2016.
 */


public class CustomerOrder implements Serializable{

    private static final long serialVersionUID = 2983360377227484514L;

   
    @Id
    private int customerOrderId;

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;


    @OneToOne
    @JoinColumn(name="shippingAddressId")
    private ShippingAddress shippingAddress;
    
    
    @ManyToMany(mappedBy = "customerOrder",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<ShippingAddress> shippingAddressId = new HashSet<>();
    
    
    

    public Set<ShippingAddress> getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Set<ShippingAddress> shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
