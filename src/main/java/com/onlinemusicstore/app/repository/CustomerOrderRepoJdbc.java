package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.models.ShippingAddress;

// for checkout the customer order this class is used
@Repository
public class CustomerOrderRepoJdbc {
	
	@Autowired
	JdbcTemplate jdbcTemplate;


	
	public List<CustomerOrder> customerOrderfindAll(){
		
		String sql = "select customer_order.customer_order_id,customer_order.cart_id, cart.grand_total, customer_order.customer_id, customer.customer_email, customer.customer_name, customer.customer_phone,shipping_address.address, shipping_address.shipping_address_id,shipping_address.address_type from customer_order inner join customer on customer_order.customer_id = customer.customer_id inner join shipping_address on shipping_address.customer_id = customer_order.customer_id  inner join cart on cart.cart_id = customer_order.cart_id inner join order_address on order_address.order_id = customer_order.customer_order_id AND shipping_address.shipping_address_id = order_address.address_id";

		String selectSql = "select customer_order.customer_order_id,customer_order.cart_id,customer_order.customer_id, customer.customer_email, customer.customer_name, customer.customer_phone,sh_ad.address as shipping_address , GROUP_CONCAT( `bl_ad`.`address` ) as \"billing_address\", cart.grand_total from customer_order left join order_address  on order_address.order_id = customer_order.customer_order_id left join customer on customer.customer_id = customer_order.customer_id left join shipping_address sh_ad on sh_ad.shipping_address_id = order_address.address_id AND sh_ad.address_type='shipping' left join shipping_address bl_ad on bl_ad.shipping_address_id = order_address.address_id AND bl_ad.address_type = 'billing' inner join cart on cart.cart_id = customer_order.cart_id  group by customer_order_id"; 
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(selectSql, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
				
				
				Cart cart = new Cart();
				Customer customer = new Customer();
				ShippingAddress shippingAddress = new ShippingAddress();
				
	
//				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				
				
				
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerName(rs.getString("customer_name"));
				
				
			//	shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				shippingAddress.setBillingAddress(rs.getString("billing_address"));
				shippingAddress.setAddress(rs.getString("shipping_address"));
			//	shippingAddress.setAddressType(rs.getString("address_type"));
				

				customerOrder.setCart(cart);
				customerOrder.setShippingAddress(shippingAddress);
				customerOrder.setCustomer(customer);
				
				
				return customerOrder;
			}
		});
		return customerOrder;
		
		
	}

	public CustomerOrder save(CustomerOrder customerOrder) {
		KeyHolder holder = new GeneratedKeyHolder();
		String insertSql = "insert into customer_order ( cart_id, customer_id) "
				+ "values ( :cartId, :customerId )";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", customerOrder.getCart().getCartId());
		parameters.addValue("customerId", customerOrder.getCustomer().getCustomerId());
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql, parameters,holder);
		customerOrder.setCustomerOrderId(holder.getKey().intValue());
		System.out.println("done");
		
		return customerOrder;
	}
	
	public void savejoin(int orderId,int addressId) {
		
		StringBuilder insertSql  = new StringBuilder("insert into order_address (order_id, address_id)");
		insertSql.append("value (:orderId, :addressId)");
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("orderId", orderId);
		parameters.addValue("addressId", addressId);
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql.toString(), parameters);
	
		System.out.println("join are done");
		
	
	}
}
