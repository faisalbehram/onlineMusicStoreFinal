  	package com.onlinemusicstore.app.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinemusicstore.app.dao.CartDao;
import com.onlinemusicstore.app.dao.CustomerDao;
import com.onlinemusicstore.app.dao.ShippingDao;
import com.onlinemusicstore.app.dao.UsersDao;
import com.onlinemusicstore.app.models.BillingAddress;
import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.models.User;

@Service
@Transactional
public class CustomerServiceImpli implements CustomerService {
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private ShippingDao shippingDao;

	

	
	@Override
	public void addCustomer(Customer customer) {
		
		
		
		
		//Cart newCart = new Cart();
		customer.setPassword(passEncoder.encode(customer.getPassword()));
		System.out.println("thepassword is saved" + customer.getPassword());
		Customer id = customerDao.saveCustomer(customer);
		
		
		// saving the user
		System.out.println("the customer id is 1" + customer.getCustomerId());
		 User newuser = new User();
		newuser.setEmail(customer.getCustomerEmail());
		newuser.setUsername(customer.getUsername());
		newuser.setPassword(customer.getPassword());
		newuser.setVerified(true);
		usersDao.saveUser(newuser);

	
//		newCart.setCustomer(id);
//		cartDao.saveCart(newCart);
		

		

		System.out.println("the customer id is " + customer.getCustomerId());
	}

	@Override
	public Optional<Customer> getCustomerById(int customerId) {
		return customerDao.getCustomerById(customerId);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerDao.getAllCustomer();
	}

	@Override
	public Customer getCustomerByUsername(String username) {
	Customer  getCustomer =  customerDao.getByUserName(username);
	   System.out.println("in Customer Serverice get by username ,, the user name is " + getCustomer);
	return getCustomer;
	}



	@Override
	public Customer getCustomerByEmail(String email) {
		 System.out.println("in Customer Serverice get by email ,, the user name is..pinding " );
			Customer customer = customerDao.getByEmail(email);
			System.out.println("in Customer Serverice get by email ,, the user name is..pinding "+  customer);
			return customer;
	}
	
	@Override
	public Customer getCustomerByEmailForId(String email) {
		 System.out.println("in Customer Serverice get by email ,, the user name is..pinding " );
			Customer customer = customerDao.getByEmailForId(email);
			System.out.println("in Customer Serverice get by email ,, the user name is..pinding "+  customer);
			return customer;
	}

	@Override
	public Customer getCustomer(int customerId) {
		Customer customer =customerDao.getCustomerById(customerId).orElseThrow();
		
		return customer;
		
	}

	@Override
	public void addCustomer(Customer customer, String shippingAddress, @Valid String billingAddress) {
		

		ShippingAddress address=new ShippingAddress();
		address.setAddressType("shipping");
		
	//	Cart newCart = new Cart();
		
		customer.setPassword(passEncoder.encode(customer.getPassword()));
		System.out.println("thepassword is saved" + customer.getPassword());
		
		Customer id = customerDao.saveCustomer(customer);
		// saving the shipping address 	
	
		address.setCustomer(id);
		address.setAddressType("shipping");
		address.setAddress(shippingAddress);

		shippingDao.saveShipping(address);
		
		// seting the billing address in same table of shipping address
		address.setAddressType("billing");
		address.setCustomer(id);
		address.setAddress(billingAddress);
		
		shippingDao.saveShipping(address);
		System.out.println("shipping saved");
		// saving the billing
		
		// saving the user
		System.out.println("the customer id is 1" + customer.getCustomerId());
		 User newuser = new User();
		newuser.setEmail(customer.getCustomerEmail());
		newuser.setUsername(customer.getUsername());
		newuser.setPassword(customer.getPassword());
		newuser.setVerified(true);
		usersDao.saveUser(newuser);

		System.out.println("the customer id is 2" + customer.getCustomerId());
		// saving the cart
	
//		newCart.setCustomer(id);
//		cartDao.saveCart(newCart);
//		
		
		
		
	}

	

}
