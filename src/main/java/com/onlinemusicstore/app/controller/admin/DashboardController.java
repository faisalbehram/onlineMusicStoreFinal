package com.onlinemusicstore.app.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.onlinemusicstore.app.beans.CustomerCountBean;
import com.onlinemusicstore.app.beans.CustomerOrderCountBean;
import com.onlinemusicstore.app.service.CustomerService;
import com.onlinemusicstore.app.service.OrderService;


@Controller
@RequestMapping("/admin")
public class DashboardController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/bar")
	public String bar () {
		return "barGraph2.jsp";
	}
	
	@RequestMapping("/linechartdata")
	@ResponseBody
	public String getBar(Model  model) {
		
		List<CustomerCountBean> customerCount =customerService.getCustomerCount();
		List<CustomerOrderCountBean> customerOrderCount = orderService.getCustomerOrderCount();
		List<CustomerOrderCountBean> orderRevenuePerMonth = orderService.getRevenuePerMonth();
		List<CustomerOrderCountBean> revenuePerProductFromOrder = orderService.getRevenuePerProductFromOrder();
		
		JsonArray jsonMon = new JsonArray();
		JsonArray jsonNumberOfCustomer = new JsonArray();
		
		
		JsonArray jsonOrderMon = new JsonArray();
		JsonArray jsonNumberOfCustomerOrder = new JsonArray();
		
		JsonArray jsonOrderMonth = new JsonArray();
		JsonArray jsonRevenuePerMonth= new JsonArray();
		
		
		JsonArray jsonProductName = new JsonArray();
		JsonArray JsonRevenueFromProduct= new JsonArray();
		JsonArray jsonTotalQuantity = new JsonArray();
		
		
		JsonObject json = new JsonObject();
		
		// Chart for CUstomer
		customerCount.forEach(data -> {
			jsonMon.add(data.getMonthToRegister()+" :Register Customer");
			jsonNumberOfCustomer.add(data.getCountCustomer());
		});
		
		
		// chart form Orders
		customerOrderCount.forEach(data -> {
			jsonOrderMon.add(data.getMonths()+" :Order Per Months");
			jsonNumberOfCustomerOrder.add(data.getCountOrder());
		});
		
		
		// chart for revenue
		orderRevenuePerMonth.forEach(data -> {
			jsonOrderMonth.add(data.getMonths()+" :Revenue Per Months");
			jsonRevenuePerMonth.add(data.getRevenuePerMonth());
		});
		
		
		// chart for revenue from per product
		revenuePerProductFromOrder.forEach(data -> {
		//	jsonTotalQuantity.add(data.getQuantityOfProduct());
			jsonProductName.add(data.getProductName() +  " /" +  data.getQuantityOfProduct());
			JsonRevenueFromProduct.add(data.getTotalRevenue() );
		});
		
		
		json.add("months", jsonMon);
		json.add("customers", jsonNumberOfCustomer);
		
		json.add("orderMonths", jsonOrderMon);
		json.add("customersOrder", jsonNumberOfCustomerOrder);
		
		json.add("revenuePerMonth",jsonOrderMonth );
		json.add("revenue", jsonRevenuePerMonth);
		
		
		json.add("productName", jsonProductName);
	//	json.add("quantity", jsonTotalQuantity);
		json.add("productRevenue", JsonRevenueFromProduct);
		/*
		 * mon.add("jan"); mon.add("feb"); mon.add("march"); mon.add("april");
		 * 
		 * num.add(6); num.add(3); num.add(10); num.add(5);
		 * 
		 * json.add("month", mon); json.add("customers", num);
		 */
		
		
		
		return json.toString();
	}

}
