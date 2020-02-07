package com.onlinemusicstore.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlinemusicstore.app.beans.CustomerOrderCountBean;
import com.onlinemusicstore.app.beans.OrderDetailBean;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.repository.CustomerOrderRepoJdbc;



@Repository
public class CustomerOrderDao{

       
//        @Autowired 
//        CustomerOrderRepository customerOrderRepo;
        
        @Autowired
        CustomerOrderRepoJdbc customerOrderRepoJdbc;
        
        public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
        	System.out.println("customer Order are saved");
        	return customerOrderRepoJdbc.save(customerOrder);
        }
        
		public List<CustomerOrder> getAllOrder() {
			
			return customerOrderRepoJdbc.customerOrderfindAll();
		}

		public void savejoin(int customerOrderId, int shippingAddressId) {
			customerOrderRepoJdbc.savejoin(customerOrderId, shippingAddressId);
			
		}

		public List<CustomerOrder> getOrderByProduct(int productId) {
		
			return customerOrderRepoJdbc.getOrderByProduct(productId);
		}

		public List<CustomerOrder> getOrderByProduct() {
			return customerOrderRepoJdbc.getOrderByProduct();
		}
		
		public List<CustomerOrder> getOrderByCategory(int categoryId) {
			
			return customerOrderRepoJdbc.getOrderByCategory(categoryId);
		}
		
		public List<CustomerOrderCountBean> getCustomerOrderCount (){
			return customerOrderRepoJdbc.getCountOfOrderByMonth();
		}
		
		public List<CustomerOrderCountBean> getRevenuePerMonth (){
			return customerOrderRepoJdbc.getRevenuePerMonth();
		}
		
		public List<CustomerOrderCountBean> getTotalRevenueFromProduct (){
			return customerOrderRepoJdbc.getRevenuePerProductFromOrder();
		}

		public List<CustomerOrder> getOrdersByCustomerId(int id) {
			
			return customerOrderRepoJdbc.getOrdersByCustomerId(id);
		}

		public List<OrderDetailBean> getOrderDetailById2(int orderId) {
			return customerOrderRepoJdbc.getOrderDetailById2(orderId);
		}
		
		

}
