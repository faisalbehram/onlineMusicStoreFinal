package com.onlinemusicstore.app.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * Created by Le on 1/24/2016.
 */

public class ShippingAddress implements Serializable{


    private static final long serialVersionUID = 989191150380037359L;


    @Id
    private int shippingAddressId;
    private String address;
    private String addressType;
    
    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "order_address",joinColumns = @JoinColumn(name= "order_id"),inverseJoinColumns = @JoinColumn(name = "address_id"))
	private Set<CustomerOrder> customerOrder =  new HashSet<>();
    
    private String billingAddress;
    private int billingAddressId;
    
    
    
    public int getBillingAddressId() {
		return billingAddressId;
	}


	public void setBillingAddressId(int billingAddressId) {
		this.billingAddressId = billingAddressId;
	}


	public String getBillingAddress() {
		return billingAddress;
	}


	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}


	public Set<CustomerOrder> getCustomerOrder() {
		return customerOrder;
	}


	public void setCustomerOrder(Set<CustomerOrder> customerOrder) {
		this.customerOrder = customerOrder;
	}


	public int getShippingAddressId() {
        return shippingAddressId;
    }

    
    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
}
