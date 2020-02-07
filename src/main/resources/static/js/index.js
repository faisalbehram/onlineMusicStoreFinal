$.ajax({
	url: 'linechartdata',
	success: function(result){
		var months = JSON.parse(result).months;
		var customers = JSON.parse(result).customers;
		
		var monthsOrder = JSON.parse(result).orderMonths;
		var customersOrder = JSON.parse(result).customersOrder;
		
		var monthsOrder = JSON.parse(result).revenuePerMonth;
		var revenue = JSON.parse(result).revenue;
		
		
	//	var productQuantity = JSON.parse(result).quantity;
		var productRevenue = JSON.parse(result).productRevenue;
		var productName = JSON.parse(result).productName;
		
		drawLineChart(months, customers);
		drawLineChart1(monthsOrder, customersOrder);
		drawLineChartForRevenue(monthsOrder,revenue);
		drawLineChartForProductRevenue(productRevenue,productName);
		
		
	}
})

function drawLineChart(months,customers){
Highcharts.chart('container', {
	chart: {
		type: 'line',
		width: 500
	},
	title:{
		text: 'Customer Registration Per Month'
	},
	xAxis: {
		title: {
			text: 'Months'
		},
		categories: months
	},
	tooltip: {
		formatter: function(){
			return '<strong> ' + this.x + ':</strong>' +this.y ;
		}
	},
	
	series: [{ 
		data: customers
	}]

});
}

function drawLineChart1(months1,customers1){
	Highcharts.chart('container1', {
		chart: {
			type: 'line',
			width: 500
		},
		title:{
			text: 'Number Of Order Per Month'
		},
		xAxis: {
			title: {
				text: 'Months'
			},
			categories: months1
		},
		tooltip: {
			formatter: function(){
				return '<strong> ' + this.x + ':</strong>' +this.y ;
			}
		},
		series: [{
			data: customers1
		}]

	});
	}


function drawLineChartForRevenue(months1,customers1){
	Highcharts.chart('containerRevenue', {
		chart: {
			type: 'line',
			width: 500
		},
		title:{
			text: 'Revenue Per Month'
		},
		xAxis: {
			title: {
				text: 'Months'
			},
			categories: months1
		},
		tooltip: {
			formatter: function(){
				return '<strong> ' + this.x + ':</strong>' +this.y ;
			}
		},
		series: [{
			data: customers1
		}]

	});
	}

function drawLineChartForProductRevenue(productRevenue,productName){

	Highcharts.chart('containerProductRevenue', {
		chart: {
			type: 'column',
			width: 500
		},
		title: {
            text: 'Revenue from Product'
        },
        xAxis: {
            title: {
                text: 'Product Name / Order Quantity'
            },
            categories: productName 
        },
        tooltip: {
			formatter: function(){
				return '<strong> ' + this.x  + ':</strong>' + "Rs:"+this.y   ;
			}
		},
        yAxis: {
            title: {
                text: ' Revenue Generated'
            },
            tickInterval: 100
        },
        series: [{ 
       
            data: productRevenue
        }]
    });
	}