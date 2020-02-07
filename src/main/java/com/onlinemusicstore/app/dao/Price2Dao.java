package com.onlinemusicstore.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.repository.PriceJdbcRepo;


@Repository
public class Price2Dao {

//	@Autowired
//	private Price2Repository price2Repository;
	
	@Autowired
	private PriceJdbcRepo jdbcRepo;
	
	// to save the price
	public void savePrice(Price2 price2) {
		jdbcRepo.savePrice(price2);
	}
	
	public void updatePrice(Price2 price2) 
	{
		jdbcRepo.updatePrice(price2);
	}
	
	
	public void save(Price2 price) {
		jdbcRepo.saveDiscountForCategory(price);
	}
	
	public void update(Price2 price) {
		jdbcRepo.updateDiscountForCategory(price);
	}
	
	
	//return list of price2 by product id no constrant are there
	public List<Price2> findByProductId(int i) {
	//	List<Price2> price = price2Repository.findByProductProductId(productId);
		List<Price2> price = jdbcRepo.findPriceByProductId(i);
		System.out.println("the product id is from priceDao is " + price);
		return price;
	}
	
	// now find the product by id and get the basic discount to show to the user
	public List<Price2> findProductForViewProduct(int productId) {

		List<Price2> checking = jdbcRepo.findByProductForViewProductDiscount(productId);
		
		return checking;
	}
	
	public Optional<Price2> findByPro(int productId) {
		
		//Optional<Price2> price = price2Repository.findProduct(productId);
		Price2 checking = jdbcRepo.findPriceByProductIdOptiona(productId);
		Optional<Price2> optionalPrice = Optional.of(checking);
		System.out.println("the product id is from priceDao is in findByPro  " + optionalPrice);
		return optionalPrice;
	}
	
	
	
	
//	public List<Price2> allPrice(){
//		return price2Repository.findAllPrices();
//	}
	
	// all basic prices
	public List<Price2> findAllBasicPrice(){
		

		
		return jdbcRepo.allBasePrice();
	}
	
	// all disocunt prices where product id is not null
	public List<Price2> findAllDiscountPrice()
	{
		return jdbcRepo.allDiscountPrice();
	}
	
	// it return all list of product where product id is not null 
	public List<Price2> findProductPriceForUpdate(int productId)
	{
		return jdbcRepo.findProductPriceForUpdate(productId);
	}
	
	// all discount where product id is null
	
	
	

	public Optional<Price2> findById(int priceId) {
	//	Optional<Price2> price2 = price2Repository.findById(priceId);
		
		Price2 checking = jdbcRepo.findByPriceId(priceId);
		Optional<Price2> optionalPrice = Optional.of(checking);
		return optionalPrice;
	}

	
	public Optional<Price2> findProductBasicPrice(int productId) {
		
		Price2 checking = jdbcRepo.findByProductGetBasePrice(productId);
		Optional<Price2> optionalPrice = Optional.of(checking);
		System.out.println("checking is "  + checking);
		
		return optionalPrice;
	}
	
	public Optional<Price2> genericDiscount() {
		Price2 checking = jdbcRepo.findGenericPrice();
		Optional<Price2> optionalPrice = Optional.of(checking);
		return optionalPrice;
	}
	
	public Price2 findTax() {
		Price2 checking = jdbcRepo.findTaxPrice();
		
		return checking;
	}


	public Price2 findCategoryDiscount(Long categoryId) {
		Price2 checking = jdbcRepo.findCategoryDiscount(categoryId);
		System.out.println("to optional converting");
		
		System.out.println("to optional converted");
		return checking;
	}

	public List<Price2> findallCategoryPrices() {
		List<Price2> price = jdbcRepo.findAllCategoryDiscount();
		return price;
	}

	public Price2 getCategoryDiscount(int productId, Long id, int parentId) {
		System.out.println("the id get " + id);
		
		Price2 price2 = jdbcRepo.getDiscountForProduct(productId,id,parentId);
		
	
		return price2;
	}

	public Price2 getAccessoryPrice(Integer id) {
		
		return jdbcRepo.getAccessoryPrice(id);
	}
}
