package com.onlinemusicstore.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.repository.AccessoryRepoJdbc;

@Repository
public class AccessoryDao {
	
	@Autowired
	AccessoryRepoJdbc accessoryRepoJdbc;

	public int saveAccessory(String accessoryName, int productId) {
		
		return accessoryRepoJdbc.saveAccessory(accessoryName,productId);
		
		
		
	}

	public List<Accessories> getAccessoriesByProductId(int productId) {
		System.out.println("the all accessories are in product by id " + productId);
		List<Accessories> accessories = accessoryRepoJdbc.accessoriesByProductId(productId);
		System.out.println("the all accessories are in product by id " + productId + "the list is " + accessories);
		
		return accessories;
	}

	public List<Accessories> getAllAccessories() {
		
		return accessoryRepoJdbc.allAccessories();
	}

	public List<Accessories> getAccessoryByCartId(int cartId) {
		
		return accessoryRepoJdbc.getAccessoryByCartId(cartId);
	}
	
	

}
