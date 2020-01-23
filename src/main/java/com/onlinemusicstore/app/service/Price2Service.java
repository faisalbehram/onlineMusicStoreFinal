package com.onlinemusicstore.app.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.dao.Price2Dao;
import com.onlinemusicstore.app.models.Category;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.PricesWithDiscount;


@Service
public class Price2Service {
	
	@Autowired
	private Price2Dao price2Dao;
	
	public List<Price2> findbyProductId(int i) {
		System.out.println("the product id in priceservice is " + i);
		List<Price2> price = price2Dao.findByProductId(i);
		return price;
	}
	
	public void saveThePrice(Price2 price2) {
				
		if(price2.getPercentageDiscount() == 0) {
			price2.setPriceType("Basic");
			price2.setToDate(null);
			System.out.println("in priceservice the percentage discount is " + price2.getPercentageDiscount() +  "the category is " + price2.getCategory());
			try {
				price2.setFromDate(dateSaving());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			price2Dao.savePrice(price2);
		}
		else 
		{
			price2.setPriceType("Discount");
			price2.setToDate(null);
			try {
				price2.setFromDate(dateSaving());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			price2Dao.savePrice(price2);
			
		}
	}
	
	
	public PricesWithDiscount getDiscountById(int productId) {
		Optional<Price2> getBasePrice = getBasicPriceForViewProduct(productId);
		
		List<Price2> price2 = price2Dao.findProductForViewProduct(productId);
		System.out.println("what is price is " + price2);
		
		
		PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
		System.out.println("the price is empty so");
		if(price2.isEmpty()) {
			System.out.println("the price is empty so1");
			pricesWithDiscount.setDiscountPercentage(0);
			pricesWithDiscount.setDiscountPrice(0);
			pricesWithDiscount.setBasicPrice(getBasePrice.get().getPrice());
			return pricesWithDiscount;
		}
		else 
		{
			
		double discount = ( price2.get(0)).getPercentageDiscount()/100;
		
		double basePrice = getBasePrice.get().getPrice();
		
		double discountPrice = basePrice - ( basePrice * discount);
		pricesWithDiscount.setDiscountPercentage(discount * 100);
		pricesWithDiscount.setDiscountPrice(discountPrice);
		pricesWithDiscount.setBasicPrice(basePrice);
		
		return pricesWithDiscount;
		}
	}
	
	public Optional<Price2> getBasicPriceForViewProduct(int productId) {
		return price2Dao.findProductBasicPrice(productId);	
	}
	
	public List<Price2> findAllBasicPrice(){
		List<Price2> allPrice = price2Dao.findAllBasicPrice();
		
		System.out.println("the price list in price2service base price  + " + allPrice.size());
		return allPrice;
		
	}
	
	public List<Price2> findAllDiscountPrice(){
		List<Price2> allPrice = price2Dao.findAllDiscountPrice();
		System.out.println("the price list in price2service all discount is  + " + allPrice.size());
		return allPrice;
		
	}
	
	
	
	public Date dateSaving() throws ParseException 
		{  
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	        System.out.println("current: " +parseFormat.parse(dateFormat.format(date)));
            return parseFormat.parse(dateFormat.format(date));    
	}

	public Optional<Price2> findById(int priceId) {
		
		return price2Dao.findById(priceId);
	}

	public void disableThePrice(Price2 price2) {
		
		price2.setFromDate(null);
		try {
			price2.setToDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(price2);
			
	}
	
	
	//new pricing is started here
	


	public Optional<Price2> findByPro(int productId) {
		return price2Dao.findByPro(productId);
		
	}
	
	
	
	public void saveGenericThePrice(double discount) {
		String discountType = "Discount";
		Price2 price2 =new Price2();
		price2.setPercentageDiscount(discount);
		price2.setPriceType(discountType);
		try {
			price2.setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(price2);
		
	}
	
	public void saveGenericThePrice(double discount, int id) {
		String discountType = "Discount";
		Price2 price2 =new Price2();
		price2.setId(id);
		price2.setPercentageDiscount(discount);
		price2.setPriceType(discountType);
		try {
			price2.setFromDate(dateSaving());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		price2Dao.savePrice(price2);
		
	}


	// save the discount when discounts are updated
	public void saveSpecilDiscount(List<Price2> price2, double discount,int i) 
	{
		String discountType = "Discount";
		//Price2 specialPrice  =new Price2();
		price2.get(i).setId(price2.get(i).getId());
		System.out.println("the id of price is" + price2.get(i).getId());
		price2.get(i).setPercentageDiscount(discount);
		price2.get(i).setPriceType(discountType);
		price2.get(i).setProduct(price2.get(i).getProduct());
		price2.get(i).setToDate(null);

		
		try {
			price2.get(i).setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.updatePrice(price2.get(i));
		
		
	}
	
	// when new discount for a price for new products comming
	public void saveSpecilDiscount(Price2 price2, double discount) 
	{
		String discountType = "Discount";
		Price2 specialPrice  =new Price2();
		
		specialPrice.setPercentageDiscount(discount);
		specialPrice.setPriceType(discountType);
		specialPrice.setProduct(price2.getProduct());
		specialPrice.setToDate(null);
		try 
		{
			specialPrice.setFromDate(dateSaving());
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		price2Dao.savePrice(specialPrice);
	}

	
	
	//new discount used now
	
	public List<PricesWithDiscount> findDiscounts3(List<Price2> allDiscountPrice) 
	{
		List<Price2> discounts = allDiscountPrice;
		List<Price2> allbasePrice = findAllBasicPrice();
		
		List<PricesWithDiscount> discountPrices = new ArrayList<>();
		
		System.out.println("in findDiscount the allDiscount Price is " + discounts.size()); 
		System.out.println("in findDiscount the basePrice size is " +allbasePrice.size()); 
		
		
		for(int i = 0; i< discounts.size(); i++) {
			
			Price2 discountPrice = discounts.get(i);
			 System.out.println("discount started 0 the discount price pro id is " + discountPrice.getProduct().getProductId() );
			
			for (int j = 0; j < allbasePrice.size(); j++) {
				 
				 
				Price2 basePrice = allbasePrice.get(j);
				System.out.println("discount started 1 base price id is" + basePrice.getProduct().getProductId());
				if(discountPrice.getProduct().getProductId() == (basePrice.getProduct().getProductId())  ) {
					
					 System.out.println("discount started");
					 
					 double discountPercentage = discountPrice.getPercentageDiscount()/100;
					 System.out.println("discountpercentage started" + discountPercentage);
					 
					 double  getBasePrice = basePrice.getPrice();
					 System.out.println("getBasePrice " + getBasePrice);
					 
					 double setDiscountPrice = getBasePrice - (getBasePrice * discountPercentage);
					 System.out.println("discountPrice " + setDiscountPrice);
					 
					 PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
					 pricesWithDiscount.setBasicPrice(getBasePrice);
					 pricesWithDiscount.setDiscountPercentage(discountPercentage);
					 pricesWithDiscount.setDiscountPrice(setDiscountPrice);
					 pricesWithDiscount.setProId(discountPrice.getProduct());
					 System.out.println("the categoy isss " + discountPrice.getCategory().getId());
					 pricesWithDiscount.setCategoryId(discountPrice.getCategory());
					 discountPrices.add(pricesWithDiscount);

				}
				
			}
			
		}
		

		return discountPrices;
		
}
	
	public List<PricesWithDiscount> findDiscountsWithCategory() 
	{
		
		
		List<Price2> allbasePrice = findAllBasicPrice();
		List<Price2> categoryPrice = findAllCategoryPrice();
		List<PricesWithDiscount> discountPrices = new ArrayList<>();
		
		for(int k=0 ; k<categoryPrice.size(); k++) {
			Price2 categoryDiscount = categoryPrice.get(k);
			System.out.println("in category size" + categoryPrice.size()); 
			System.out.println("the categorypriceid is  "+categoryPrice.get(k).getCategory().getId()); 
			System.out.println("the categgory priceid is  "+ categoryPrice.get(k).getPercentageDiscount());
			

		
			
			for (int i=0; i<allbasePrice.size();i++)
			{
				Price2 basePrice = allbasePrice.get(i);
				System.out.println(" the parent id is "  + basePrice.getCategory().getParentId());
				System.out.println(" now for and the baseprice id is " + allbasePrice.get(i).getCategory().getId() + "and the category id is " + categoryPrice.get(k).getCategory().getId() );
					if(categoryDiscount.getCategory().getId().equals(basePrice.getCategory().getId()) || categoryDiscount.getCategory().getId() == basePrice.getCategory().getParentId()) 
					{
						
						System.out.println("discount started");
						 
						 double discountPercentage = categoryDiscount.getPercentageDiscount()/100;
						 System.out.println("discountpercentage started" + discountPercentage);
						 
						 double  getBasePrice = basePrice.getPrice();
						 System.out.println("getBasePrice " + getBasePrice);
						 
						 double setDiscountPrice = getBasePrice - (getBasePrice * discountPercentage);
						 System.out.println("discountPrice " + setDiscountPrice);
						 
						 PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
						 pricesWithDiscount.setBasicPrice(getBasePrice);
						 pricesWithDiscount.setDiscountPercentage(discountPercentage);
						 pricesWithDiscount.setDiscountPrice(setDiscountPrice);
						 pricesWithDiscount.setProId(basePrice.getProduct());
						 pricesWithDiscount.setCategoryId(categoryDiscount.getCategory());
						 discountPrices.add(pricesWithDiscount);
						
					}
			
			}
			
			for(int test = 0 ; test< discountPrices.size(); test++) {
				
				System.out.println("category discount detrail is started" +  discountPrices.get(test).getCategoryId().getId());
				System.out.println("category discount detrail is started" +  discountPrices.get(test).getDiscountPrice());
				System.out.println("category discount detrail is started" +  discountPrices.get(test).getProId().getProductId());
				System.out.println("category discount detrail is started" +  discountPrices.get(test).getCategoryId().getParentId());
				
				
			}
			
		}
		return discountPrices;
		
}

	
	
	
	
	public List<Price2> findProductPriceForUpdate(int productId){
		return price2Dao.findProductPriceForUpdate(productId);
	}
	
	
	public PricesWithDiscount findGenericDiscount(int productId) {
		Optional<Price2> price2 =price2Dao.genericDiscount();
		Optional<Price2> basePrices = price2Dao.findProductBasicPrice(productId);
		PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
		if(!price2.isEmpty()) {
			double discountPercentage = price2.get().getPercentageDiscount();
			double basePrice = basePrices.get().getPrice();
			double discountPrice = basePrice - ((basePrice * discountPercentage)/100);
			
			
			pricesWithDiscount.setBasicPrice(basePrice);
			pricesWithDiscount.setDiscountPercentage(discountPercentage);
			pricesWithDiscount.setDiscountPrice(discountPrice);
			
		}
			System.out.println("the Generic Discount is " + price2);		
		return pricesWithDiscount ;
	}

	public Optional<Price2> findGenericAllDiscount(){
		Optional<Price2> price2 =price2Dao.genericDiscount();
		return price2;
	}

	public Price2 findCategoryDiscount(Long categoryId) {
		return price2Dao.findCategoryDiscount(categoryId);
		
	}

	public void saveCategoryDiscount(double discount, Category categoryId) {
		String discountType = "Discount";
		Price2 price2 =new Price2();
		price2.setPercentageDiscount(discount);
		price2.setPriceType(discountType);
		price2.setCategory(categoryId);
		
		try {
			price2.setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.save(price2);
		
		
	}

	public void updatingCategoryDiscount(double discount, int genericId, Category category) {
		String discountType = "Discount";
		Price2 price2 =new Price2();
		price2.setId(genericId);
		price2.setPercentageDiscount(discount);
		price2.setPriceType(discountType);
		price2.setCategory(category);
		try {
			price2.setFromDate(dateSaving());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2.setProduct(null);
		price2Dao.update(price2);
		
	}

	public List<Price2> findAllCategoryPrice() {
		List<Price2> price = price2Dao.findallCategoryPrices();
		return price;
	}

	public PricesWithDiscount getProductDiscount(int productId, Long id,int parentId) {
		Optional<Price2> basePrice = getBasicPriceForViewProduct(productId);
		Price2 pricesWithDiscount=	price2Dao.getCategoryDiscount(productId,id,parentId);
		
		double base = basePrice.get().getPrice();
		double percentage = pricesWithDiscount.getPercentageDiscount();
		double discountPrice = base - ((percentage * base)/100);
		System.out.println("base price is " + base  + "percentage is " + percentage + "discount price is" + discountPrice);
		
		

		PricesWithDiscount pricesWithDiscount2 = new PricesWithDiscount();
		pricesWithDiscount2.setBasicPrice(base);
		pricesWithDiscount2.setDiscountPrice(discountPrice);
		pricesWithDiscount2.setDiscountPercentage(percentage);
		
		
		return pricesWithDiscount2;
	}

	public String updateDiscount(double discount, int productId) {
		
		if(productId != 0) 
		{
		
				System.out.println("when All is not");
				List<Price2> price2 = findProductPriceForUpdate(productId);
				if(price2.isEmpty()) {
					System.out.println("if it is empty so");
					Optional<Price2> getProductId = findByPro(productId);
					Price2 newPrice = new Price2();
					newPrice.setProduct(getProductId.get().getProduct());
	
					saveSpecilDiscount(newPrice, discount);
					return "admin.jsp";
				}
		
				System.out.println("the price2 size is to update the price2 " + price2);
				for(int i =0; i<price2.size();i++) {
					
						if(price2.get(i).getProduct().getProductId() == productId ) {
							saveSpecilDiscount(price2, discount,i);
							break;
						}	
				}
		
		
		}
		else 
		{
				System.out.println("checking generic if present is");
				Optional<Price2> findGenericDiscountId =findGenericAllDiscount();
				if(findGenericDiscountId.isEmpty())
					{
					System.out.println("saving new Generic");
					saveGenericThePrice(discount);
					}
				else {
					System.out.println("the all product generic discount is");
					
					int genericId = findGenericDiscountId.get().getId();
					System.out.println("Update  generic discount is");
					saveGenericThePrice(discount, genericId);
					}
					
		}
		
		return "admin.jsp";
	}

	public double getAccessoryPrice(List<Integer> accessoryId) {
		double allPrice = 0;

		for (int i = 0; i < accessoryId.size(); i++) {
			Price2 prices = price2Dao.getAccessoryPrice(accessoryId.get(i));
			allPrice = allPrice + prices.getPrice();
		}
		return allPrice;
	}

	public String updateTax(double tax) {
		
		Price2	 taxPrice = price2Dao.findTax();
		System.out.println("if null");
		if (taxPrice == null) {

			System.out.println("in saving the price");
			Price2 price = new Price2();
			price.setPercentageDiscount(tax);
			price.setPriceType("Tax");
			try {
				price.setFromDate(dateSaving());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			price2Dao.savePrice(price);
		}
		else {
			
			System.out.println("in updating the price");
			
			Price2 price = new Price2();
			price.setId(taxPrice.getId());
			price.setPercentageDiscount(tax);
			price.setPriceType("Tax");
			try {
				price.setFromDate(dateSaving());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			price2Dao.updatePrice(price);
		}
		
		return "admin.jsp";
	}

	public Price2 addTax(double baseprice, double discount) {
		double taxPercent = discount/100;
		
		double priceAfterTax = baseprice + (taxPercent * baseprice);
		Price2 price = new Price2();
		price.setPrice(priceAfterTax);
		price.setPercentageDiscount(taxPercent);
		System.out.println("the tax is " + taxPercent + "the tax price is "+ priceAfterTax + "the base price is " + baseprice);
		return price;
		
	}

	
}
