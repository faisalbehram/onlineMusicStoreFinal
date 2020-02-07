<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/template/header.jsp"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>



<div class="container-wrapper">
    <div class="container">
   
   	 <table class="table table-striped table-hover">
   	 
   	 <thead>
            <tr class="bg-success">
                <th>Category Name</th>
                <th>Discount Percentage</th>
                <th>SubCategory</th>
                <th>Discount Percentage</th> 
            </tr>
            </thead>
             
            <c:forEach items="${mainCategories}" var="mainCategory" >	
            
            <tr> 
            	<td>  
						  ${mainCategory.categoryName}</td>
					<td>	  ${mainCategory.price.percentageDiscount}
		   	 </td>  
						   
		         <c:forEach items = "${subCategories }" var="subCategory">
		            
		          			<c:if test="${mainCategory.id == subCategory.parentId}">
		         	 			<tr>
		          					<td></td>
			           			 	<td> </td>
								  	<td>${subCategory.categoryName}  </td>
							  		<td> ${subCategory.price.percentageDiscount} </td>
							  </tr>
							  </c:if>
					 </c:forEach> 
					
				
					
		            		       
            </tr>
            </c:forEach>
          
   	  
   	 </table>
   <hr>
   
        <table class="table table-striped table-hover">
            <thead>
          <tr class="bg-success">
                <th>Add Main Category</th>
                <th>Add SubCategory</th>
            </tr>
            </thead>
           
            
            <tr>
             <td>
            <form action="/admin/addMainCategory" >
           
            	<input type="text" name= "mainCategory" >
            	<input type="submit" value="Add MainCategory"> 
     	 </form>
     	 
              </td>
             <td>
             <form action="/admin/addSubCategory">
            	<select name="MainCategory">
            	
            		 
            		  <c:forEach items="${mainCategories}" var="mainCategory" >	 
				  
				  <option value="${mainCategory.id}"> ${mainCategory.categoryName} </option>
				   	
				   	 </c:forEach> 
				</select>
				<input type="text" name = "subCategory" >
				<input type="submit" value="add SubCategory">
			</form>
				
            </td>
            
            
            	
            
             
         
            </tr>
          
        </table>
   
     
       <%@include file="/template/footer.jsp" %>

</div></div>
<%-- 
<select name="productId">
   <c:forEach items="${products}" var="product">
           
	        
		  	 <option value="${product.productId}">${product.productId}</option>
 
                    <td>${product.productId}</td>
                    
                    <td><a href="<spring:url value="/viewProduct/${product.productId}" />"
                    ><span class="glyphicon glyphicon-info-sign"></span></a></td>
                </c:forEach>
                 </select>
	     </form> --%>