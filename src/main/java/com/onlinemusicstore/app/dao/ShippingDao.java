package com.onlinemusicstore.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.repository.ShippingJdbcRepo;

@Repository
public class ShippingDao {
	
//	@Autowired
//	private ShippingRepository shippingRepository;
	
	@Autowired
	private ShippingJdbcRepo shippingJdbcRepo;
	
	public ShippingAddress saveShipping(ShippingAddress address) {
		
		System.out.println("the shiping are saved + " );
	
		ShippingAddress id = shippingJdbcRepo.savingShipping(address);
	
		return id;
	}

	public ShippingAddress findShippingAddress(String shipping,int cartId) {
		
		return shippingJdbcRepo.findShippingAddress(shipping,cartId);
	}

	public ShippingAddress findBillingAddress(String billing,int cartId) {
		// TODO Auto-generated method stub
		return shippingJdbcRepo.findBillingAddress(billing,cartId);
	}

	public void updateShipping(int shippingId, String shippingAddress) {
		shippingJdbcRepo.updateShipping(shippingId,shippingAddress);
		
	}

	public void updateBilling(int billingId, String billingAddress) {
		shippingJdbcRepo.updateShipping(billingId ,billingAddress);
		
	}

}
