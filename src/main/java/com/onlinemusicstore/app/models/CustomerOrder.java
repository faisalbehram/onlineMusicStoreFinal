package com.onlinemusicstore.app.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Le on 1/24/2016.
 */


public class CustomerOrder implements Serializable{

    private static final long serialVersionUID = 2983360377227484514L;

   

    private int customerOrderId;

    private Timestamp to_date;
    
    
    private Cart cart;

  
    private Customer customer;



    private ShippingAddress shippingAddress;
    
    
	private Set<ShippingAddress> shippingAddressId = new HashSet<>();
    
    
    

	
   

	public Timestamp getTo_date() {
		return to_date;
	}

	public void setTo_date(Timestamp to_date) {
		this.to_date = to_date;
	}

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
