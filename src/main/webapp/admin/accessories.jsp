<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/template/header.jsp"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>



<div class="container-wrapper">
    <div class="container">
   
   	 
  
   
   
       <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Product Name</th>
                <th>Add Accesseries Name</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
            </thead>
           
          
            <tr>
            <form action="/admin/addAccessory">
           
           	    <td>
            	<select name="productId">
            
            		  <c:forEach items="${products}" var="product">		  
				  
				  <option value="${product.productId}"> ${product.productName} </option>
				   	
				   	 </c:forEach> 
				</select>
            </td>
            <td>
            	<input type="text" name= "accessory" >
            </td>
            <td>
            	<input type="text" name= "accessoryPrice" >
            </td>
     
            
            <td><input type="submit" value="Add Accessory"></td>
           </tr>
           </form>
 </table>
   
     
       <%@include file="/template/footer.jsp" %>

</div></div>
