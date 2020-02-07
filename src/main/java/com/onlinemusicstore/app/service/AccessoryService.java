package com.onlinemusicstore.app.service;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.dao.AccessoryDao;
import com.onlinemusicstore.app.models.Accessories;
import com.onlinemusicstore.app.models.Price2;

@Service
public class AccessoryService {
	
	@Autowired
	AccessoryDao accessoryDao;
	
	@Autowired
	Price2Service price2Service;

	
	public void addAccessory(String accessoryName, int productId,int accessoryPrice) {
		Price2 price2 = new Price2();
		price2.setPrice(accessoryPrice);
		price2.setPercentageDiscount(0);
		price2.setProduct(null);
		price2.setCategory(null);
		
		int accessoryId = accessoryDao.saveAccessory(accessoryName,productId);
		Accessories accessories = new Accessories();
		accessories.setAccessoryId(accessoryId);
		System.out.println("the accessory id id " + accessoryId);
		price2.setAccessories(accessories);
		price2Service.saveThePrice(price2);
		
	}

	public List<Accessories> accessoriesByproductId(int productId){
		return accessoryDao.getAccessoriesByProductId(productId);
	}

	public List<Accessories> allAccessories() {
		
		return accessoryDao.getAllAccessories();
	}

	public List<Accessories> getAccessoryByCartId(int cartId) {
		
		return accessoryDao.getAccessoryByCartId(cartId);
	}
	
public List<Accessories> getAllAccessoryWithProduct() {
		
		return accessoryDao.getAllAccessoryWithProduct();
	}

	
}
