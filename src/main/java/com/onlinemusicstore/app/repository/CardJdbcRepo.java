package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;

@Repository
public class CardJdbcRepo {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Cart savingCart(Cart cart) {
		StringBuilder sql = new StringBuilder();
		KeyHolder holder = new GeneratedKeyHolder();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		
			System.out.println("in saving cart");
			sql.append("insert into cart (grand_total, customer_id,in_process) ");
			sql.append("values (:grand_total, :customer_id,:in_process)");
				
			parameters.addValue("grand_total", cart.getGrandTotal());
			parameters.addValue("customer_id", cart.getCustomer().getCustomerId());
			parameters.addValue("in_process", 1);

		
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			
			namedParameterJdbcTemplate.update(sql.toString(), parameters,holder);
			cart.setCartId(holder.getKey().intValue());
			return cart;
	}
	
	// when  ever items added by the same customer into it will update the cart..
	public void updateingCart(Cart cart) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
			System.out.println("in updating cart");
			 sql.append("update cart set cart_id = :cart_id , grand_total = :grand_total, customer_id = :customer_id  where cart_id = :cart_id");
			 parameters.addValue("cart_id", cart.getCartId());
	
			 parameters.addValue("grand_total", cart.getGrandTotal());
			 parameters.addValue("customer_id", cart.getCustomer().getCustomerId());
		
				
			 NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				
			 namedParameterJdbcTemplate.update(sql.toString(), parameters);
		
	}


	// it will just find the cart. if present or not
	public Cart findById(int cartId) {
		String sql = "select * from cart where cart_id = :cartId AND in_process = 1 ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Cart cart = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Cart>() {

			@Override
			public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				Customer customer = new Customer();
			
				customer.setCustomerId(rs.getInt("customer_id"));
				cart.setCustomer(customer);
				return cart;
			}
		}));
		
		return cart;
	}
	
	// this will be done checkout the cart to order completion
	public List<Cart> findByCartIdForCheckOut(int cartId) {
		String sql = "select cart.cart_id,cart.grand_total,customer.customer_id,customer.customer_name,customer.customer_email,customer.customer_phone,shipping_address.shipping_address_id,shipping_address.address,shipping_address.address_type from cart inner join customer  on cart.customer_id = customer.customer_id inner join shipping_address on shipping_address.customer_id = customer.customer_id where cart_id = :cartId ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Cart> cart = namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Cart>() {

			@Override
			public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				Customer customer = new Customer();
			
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				
				ShippingAddress shippingAddress = new ShippingAddress();
				shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				shippingAddress.setAddress(rs.getString("address"));
				shippingAddress.setAddressType(rs.getString("address_type"));
					
				customer.setShippingAddress(shippingAddress);
				cart.setCustomer(customer);
				return cart;
			}
		});
		
		return cart;
		
	}
	
	// it just take the cart for checkout and and take billing address from shipping table
	public Cart findByCartIdForCheckoutBillingAddress(int cartId) {
		String sql = "select cart.cart_id,cart.grand_total,customer.customer_id,customer.customer_name,customer.customer_email,customer.customer_phone,shipping_address.shipping_address_id,shipping_address.address,shipping_address.address_type from cart inner join customer  on cart.customer_id = customer.customer_id inner join shipping_address on shipping_address.customer_id = customer.customer_id where cart_id = :cartId  AND shipping_address.address_type = 'billing'";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Cart cart = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Cart>() {

			@Override
			public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				Customer customer = new Customer();
			
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				
				ShippingAddress shippingAddress = new ShippingAddress();
				shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				shippingAddress.setAddress(rs.getString("address"));
				shippingAddress.setAddressType(rs.getString("address_type"));
					
				customer.setShippingAddress(shippingAddress);
				cart.setCustomer(customer);
				return cart;
			}
		}));
		
		return cart;
		
	}

	public void updateTheCartProcess(Cart cart) {
		String sql = "update cart set in_process = 0 where cart_id = :cartId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cart.getCartId());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		
	}

	public void deleteTheCart(Cart cart) {
		String sql = "delete  from cart where cart_id =  :cartId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cart.getCartId());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		
	}
	

}

