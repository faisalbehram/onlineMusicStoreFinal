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
                <th>Price</th>
                <th> Discount Percentage</th>
                <th> Discount Price</th>  
            </tr>
            </thead>
             
            <c:forEach items="${products}" var="product" >	
            
            <tr>            		
            				 
            				
            				<td>  
						  ${product.productName}
						   	 </td>  
						   	 <c:forEach items="${listOfBasePrices}" var="basicprice"> 
                  		<c:if test="${basicprice.product.productId == product.productId }">
             				<td>${basicprice.price}</td>
             			</c:if>	          
           			 </c:forEach > 
           			 
           			  
            		<c:forEach  items="${mainCategories}" var="mainCategory">
            		
            		<c:if test=" ${mainCategory.id == product.category.id}">
            		  dd
            		</c:if>
            		</c:forEach>
            		
            		
		            <c:forEach items = "${discountPrices }" var="discountPrice">
		            
		          	<c:if test="${discountPrice.product.productId == product.productId }">
		           		
		           		 <td>  	
						  ${discountPrice.percentageDiscount}
						  </td>
						 
								  <c:forEach items = "${discounts }" var="discount">
								  <c:if test="${  discount.proId.productId == discountPrice.product.productId }">
											   <td>  	
											  ${discount.discountPrice}
											  </td>
											  </c:if>
											
								  	</c:forEach>
						 </c:if>
						  </c:forEach> 
					
				
					
		            		       
            </tr>
            </c:forEach>
            <tr bgcolor="#00ff00"	>
            
            <c:if test="${not empty genericDiscount}">
            	<td  bgcolor="#00ff00">the Generic Discount is</td>
            	<td></td>
            	<td>${ genericDiscount.get().percentageDiscount} </td>
            	<td></td>
            </c:if>
                     
            </tr> 
   	  
   	 </table>
   <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
	            <th>Tax</th>
	            <th>Update</th>
   			</tr>
   			</thead>
   			<tr>
   			<form action="/admin/updateTax">
   				<td><input name="tax" type="text"> </td>
   				<td><input type="submit" value="update"></td>
   			</form>
   			
   			</tr>
   </table>
   
       <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Discount</th>
                <th>All Products / Name</th>
                <th>Update Discount</th>
                <th>Disable Discount</th>  
            </tr>
            </thead>
           
          
            <tr>
            <form action="/admin/updateDiscount">
           
            <td><input type="text" name= "discount" >  </td>
     
             
             <td>
            	<select name="allProduct">
            	<option  value="all"  selected="selected">All Product</option>
            		 
            		  <c:forEach items="${products}" var="product">		  
				  
				  <option value="${product.productId}"> ${product.productName} </option>
				   	
				   	 </c:forEach> 
				</select>
				
            </td>
            
            <td><input type="submit" value="Update"></td>
            	
            <td>	
              </form>
              
              <form action="/admin/disableDiscount">
            	<select name="priceId">
            	<option  value="all"  selected="selected">All Product</option>
            		 
            		  <c:forEach items="${discountPrices}" var="discountPrice">		  
				  
				  <option value="${discountPrice.id}"> ${discountPrice.product.productName} </option>
				   	
				   	 </c:forEach> 
				</select>
            	 <td><input type="submit" value="Disable"></td>
            	</form></td>
            </tr>
         
          <tr>
          
			
         	    
            <form action="/admin/updateCategoryDiscount">
           
            <td>
            	<input type="text" name= "discount" >
            </td>
     <td>
     <select name="allCategories">            	
            	<option  value="all"  selected="selected">Category</option>
            		 
            		  <c:forEach items="${mainCategories}" var="mainCategory">		
            		  	<optgroup label=" ${mainCategory.categoryName}">
				  	  		<option value="${mainCategory.id} ">  ${mainCategory.categoryName}
				  		
				  				<c:forEach items = "${subCategories }" var="subCategory">
				  			
				  					<c:if test="${mainCategory.id == subCategory.parentId}">
				  		
				  						<option value="${subCategory.id}"> ${subCategory.categoryName}   </option>
				  					</c:if>
				  				</c:forEach>
					  		</option>
					 	 </optgroup>  
				   	 </c:forEach> 
				</select>
     </td>
            
            <td><input type="submit" value="Update"></td>
            	
           
            </tr>
           
        </table>
   		  
   		 
     
       <%@include file="/template/footer.jsp" %>

       
</div></div>
