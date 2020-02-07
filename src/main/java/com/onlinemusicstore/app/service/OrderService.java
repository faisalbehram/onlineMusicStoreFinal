package com.onlinemusicstore.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.beans.CustomerOrderCountBean;
import com.onlinemusicstore.app.beans.OrderDetailBean;
import com.onlinemusicstore.app.dao.CustomerOrderDao;
import com.onlinemusicstore.app.dao.ShippingDao;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.models.ShippingAddress;

@Service
public class OrderService {

	@Autowired
	private ShippingDao shippingDao;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerOrderDao  customerOrderDao;
	
	@Transactional
	 public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		CustomerOrder newOrder = customerOrderDao.addCustomerOrder(customerOrder);
		cartService.updateTheCartProcess(customerOrder.getCart());
	        return newOrder;
	        
	    }
	
	public ShippingAddress checkShipping(String shipping, int cartId) {
		ShippingAddress shippingAddress = shippingDao.findShippingAddress(shipping,cartId);
	 	
    	if(shippingAddress == null) {
    		System.out.println("the shippingaddress  is null not found so select the new one");
    		ShippingAddress newShippingAddress = new ShippingAddress();
    		newShippingAddress.setAddress(shipping);
    		return newShippingAddress;	
    		
    	}else {
    		System.out.println("the shipping is not null mean old was found select");
    		return null;
    	}
    	
		
	}
	public ShippingAddress checkBilling(String billing, int cartId) {
		ShippingAddress billingAddress = shippingDao.findBillingAddress(billing,cartId);   
    	if(billingAddress == null) {
    		System.out.println("the billing is null");
    		ShippingAddress newBillingAddress = new ShippingAddress();
    		newBillingAddress.setAddress(billing);
    		return newBillingAddress;
    		
    	}else {
    		System.out.println("the billing is not null");
    		return null;
    	}
    	
    	
	}

	public ShippingAddress saveNewShippingAddress(Customer customer, String address) {
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAddress(address);
		shippingAddress.setAddressType("shipping");
		shippingAddress.setCustomer(customer);
		return shippingDao.saveShipping(shippingAddress);
		
	}

	public ShippingAddress saveNewBilling(Customer customer, String address) {
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAddress(address);
		shippingAddress.setAddressType("billing");
		shippingAddress.setCustomer(customer);
		return shippingDao.saveShipping(shippingAddress);
		
		
	}

	public void orderConfirmation(int cartId, String shipping, String billing) {
		
		// it will check the all shipping and billing address if present use the older id and if not present use the new ids and save the new address
		
		ShippingAddress checkShipping = checkShipping(shipping,cartId);
    	ShippingAddress checkBilling = checkBilling(billing,cartId);
    	
    	List<Cart> cart=cartService.getCartbyIdForCheckOut(cartId);
    	
    	 CustomerOrder customerOrder = new CustomerOrder();
    	 customerOrder.setCart(cart.get(0));
         Customer customer = cart.get(0).getCustomer();
         customerOrder.setCustomer(customer);
         customerOrder.setShippingAddress(customer.getShippingAddress());
         CustomerOrder order=addCustomerOrder(customerOrder);
         
    	if(checkShipping != null ) {
    		System.out.println("saving the new shiiping address");
    		ShippingAddress newShipping = saveNewShippingAddress(cart.get(0).getCustomer(), shipping);
    		saveJoin(order.getCustomerOrderId(), newShipping.getShippingAddressId());
    	}
    	else
    	{
    		 saveJoin(order.getCustomerOrderId(),cart.get(0).getCustomer().getShippingAddress().getShippingAddressId());
    	}
    	
    	if(checkBilling != null) {
    		System.out.println("saving the new billing address");
    		ShippingAddress newBilling = saveNewBilling(cart.get(0).getCustomer(), billing);
            saveJoin(order.getCustomerOrderId(),newBilling.getShippingAddressId());
    	}
    	else {
    		 saveJoin(order.getCustomerOrderId(),cart.get(1).getCustomer().getShippingAddress().getShippingAddressId());
    	}
    
    
	}
	
	public void saveJoin(int customerOrderId, int shippingAddressId) 
	{
		System.out.println("the shippingaddress id " + shippingAddressId + "the customer order id is " + customerOrderId );
		
		customerOrderDao.savejoin(customerOrderId,shippingAddressId);
		
	}
	
	 public double getCustomerOrderGrandTotal(int cartId) {
	        double grandTotal=0;
	        Cart cart = cartService.getCartbyId(cartId);
	        List<CartItem> cartItems = cart.getCartItems();

	        for (CartItem item : cartItems) {
	            grandTotal+=item.getTotalPrice();
	        }

	        return grandTotal;
	    }
	 

	    public List<CustomerOrder> listOfCustomer(){
	    	
	    	return customerOrderDao.getAllOrder();
	    	
	    }
	    
		 public List<CustomerOrder> listOfOrderByProduct(int productId){
			    	
			    	return customerOrderDao.getOrderByProduct(productId);	    	
			    }
		
		public List<CustomerOrder> listOfOrderByProduct() {
			return customerOrderDao.getOrderByProduct();	 
		
		}
		
		public List<CustomerOrder> listOfOrderByCategoryId(int categoryId){
			
			return customerOrderDao.getOrderByCategory(categoryId);    	
		}
		    
		public List<CustomerOrderCountBean> getCustomerOrderCount(){
			return customerOrderDao.getCustomerOrderCount();
		}
		
		public List<CustomerOrderCountBean> getRevenuePerMonth(){
			return customerOrderDao.getRevenuePerMonth();
		}
		
		public List<CustomerOrderCountBean> getRevenuePerProductFromOrder(){
			return customerOrderDao.getTotalRevenueFromProduct();
		}

		public List<CustomerOrder> getOrdersByCustomerId(int id) {
		
			return customerOrderDao.getOrdersByCustomerId(id);
		}

		public List<OrderDetailBean> getOrderDetailById(int orderId) {
			
			return customerOrderDao.getOrderDetailById2(orderId);
		}
		    	
}
