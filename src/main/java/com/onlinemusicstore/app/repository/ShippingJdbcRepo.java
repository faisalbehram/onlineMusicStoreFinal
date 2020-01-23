package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.ShippingAddress;

@Repository
public class ShippingJdbcRepo  {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public ShippingAddress savingShipping(ShippingAddress shippingAddress) {
		KeyHolder holder = new GeneratedKeyHolder();
		StringBuilder sql = new StringBuilder("insert into shipping_address (address, customer_id, address_type)");
		sql.append( "values (:address, :customer_id, :address_type)");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("address", shippingAddress.getAddress());
		parameters.addValue("customer_id", shippingAddress.getCustomer().getCustomerId());
		parameters.addValue("address_type", shippingAddress.getAddressType());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
		namedParameterJdbcTemplate.update(sql.toString(), parameters,holder);
		shippingAddress.setShippingAddressId(holder.getKey().intValue());
		
		return shippingAddress;
	}

	public ShippingAddress findShippingAddress(String shipping,int cartId) {
		String sql = "select * from shipping_address inner join cart on cart.customer_id = shipping_address.customer_id where address = :address AND cart.cart_id = :cartId AND shipping_address.address_type = 'shipping'";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("address", shipping);
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		ShippingAddress ship = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql.toString(), parameters,new RowMapper<ShippingAddress>() {

			@Override
			public ShippingAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
				ShippingAddress address = new ShippingAddress();
				address.setShippingAddressId(rs.getInt("shipping_address_id"));
				address.setAddress(rs.getString("address"));
				address.setAddressType(rs.getString("address_type"));
				return address;
				
			}
		}));
		
		return ship;
	}

	public ShippingAddress findBillingAddress(String billing,int cartId) {
		String sql = "select * from shipping_address inner join cart on cart.customer_id = shipping_address.customer_id where address = :address AND cart.cart_id = :cartId AND shipping_address.address_type = 'billing'";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("address", billing);
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		ShippingAddress ship = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql.toString(), parameters,new RowMapper<ShippingAddress>() {

			@Override
			public ShippingAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
				ShippingAddress address = new ShippingAddress();
				address.setShippingAddressId(rs.getInt("shipping_address_id"));
				address.setAddress(rs.getString("address"));
				address.setAddressType(rs.getString("address_type"));
				return address;
				
			}
		}));
		
		return ship;
	}

}
