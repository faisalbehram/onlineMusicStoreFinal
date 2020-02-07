<%--
  Created by IntelliJ IDEA.
  User: Le
  Date: 1/7/2016
  Time: 7:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@include file="/template/header.jsp"%>
<style>
.center {height: 400px; display: inline-block; border: 3px solid green;margin: 10px;
}
</style>

<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Administrator page</h1>
			<h3 style="color: red;">${categoryMsg}</h3>
            <p class="lead">This is the administrator page!</p>
        
			
	<h1>Welcome to Highcharts</h1>
	
	
	
	<div  id="container" class="center"></div>
	<div  id="container1" class="center"></div><br>
	<div id="containerRevenue" class="center"></div>
	<div id="containerProductRevenue" class="center"></div>
	<script src="/js/highcharts.js"></script>
	<script src="/js/jquery.js"></script>
	<script src="/js/index.js"></script>
	</div>
	

	<div style="display: inline;">
        <h3>
            <a href="/admin/productInventory">Product Inventory</a>
        </h3>

        <p>Here you can view, check and modify the product inventory!</p>
		
        <br><br>
        
        <h3>
            <a href="/admin/bar">ADMIN Bar</a>
        </h3>

        <p>Here you can view, check and modify the product inventory!</p>
		</div>
        <br><br>
        
          <h3>
            <a href="/admin/addCategory">Add  Category</a>
        </h3>

        <p>Here you can view, and Add category</p>
		
        <br><br>
      
      <h3>
            <a href="/admin/accessory">Add  Accessory</a>
        </h3>

        <p>Here you can view, and Add category</p>
		
        <br><br>
      
     

        <h3>
            <a href="/admin/pricing">Pricing Product</a>
        </h3>

        <p>Here you can view, check and modify the product inventory!</p>
        <br>

        <h3>
            <a href="<c:url value="/admin/customer" />" >Customer Management</a>
        </h3>

        <p>Here you can view the customer information!</p>
			
		
		
        <h3>
            <a href="<c:url value="/admin/order2" />" >Order Management</a>
        </h3>

        <p>Here you can view the customer information!</p>
 
 			 
 		
 		
 		        <h3>
            <a href="<c:url value="/admin/orderViewByProduct" />" >Order Management by product</a>
        </h3>

        <p>Here you can view the customer information!</p>
 
 
        
        <a  href="/admin/size">Size</a>
	</div>		
   </div>
     

