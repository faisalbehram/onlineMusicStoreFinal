<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/template/header.jsp"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<script>
    $(document).ready(function(){
        var searchCondition = '${searchCondition}';

        $('.table').DataTable({
            "lengthMenu": [[1,2,3,5,10,-1], [1,2,3,5,10, "All"]],
            "oSearch": {"sSearch": searchCondition}
        });
    });

</script>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>All Products</h1>

            <p class="lead">Checkout all the awesome products available now!</p>
        </div>
		  <form action="/searchproduct" class="form-inline">
	        <div class="form-group mb-2">
	         <input type="text" class="form-control" name="productName" placeholder="Search products" />
	         <input type="submit" value="Search"  class="btn btn-primary"/>
	        </div>
	     </form>
	      
 		<form action="/searchProductByCategory">
            	<select name="MainCategory">
            		  <c:forEach items="${mainCategories}" var="mainCategory" >	 
				
				  <option value="${mainCategory.id}"> ${mainCategory.categoryName} </option>
				   	
				   	 </c:forEach> 
				</select>
				   <input type="submit" value="Search" />
			</form>
     	 
                 <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Photo Thumb</th>
                <th>Product Name</th>
                <th>Category</th>
                <th>Condition</th>
                <th>Price</th>
                <th>Discounted Price</th>
              
            </tr>
            </thead>
            <c:forEach items="${categories}" var="category" >
                <tr>
                    <td><img src="<c:url value="/resources/images/${category.product.productId}.png" /> " alt="image" style="width:100%"/></td>	
                    <td>${category.product.productName}</td>
                    <td>
                      			<c:forEach items="${subCategories}" var="subCategory" >
                      			<c:if test="${category.id == subCategory.id || category.id == subCategory.parentId}">
                      			
                      			> ${subCategory.categoryName}
                      			
                      			 
                      			 </c:if>
                      			 </c:forEach>
                      			 </td>
                   	
                    
                    <td>${category.product.productCondition}</td>
                     <c:forEach items="${basicPrice}" var="basicprice"> 
                  		<c:if test="${basicprice.product.productId == category.product.productId }">
             				<td>${basicprice.price}</td>
             			</c:if>	          
           			 </c:forEach>
           			
           			
            		<c:forEach items="${discounts}" var="discountprice" >
            				
            				 <c:if test="${discountprice.proId.productId == category.product.productId }">
            		
         
           					<td>${discountprice.discountPrice}</td>
           					
            			</c:if>
            				
            		
            		</c:forEach>
                    <td><a href="<spring:url value="/viewProduct/${category.product.productId}" />"
                    ><span class="glyphicon glyphicon-info-sign"></span></a>
                     
                </tr>
            </c:forEach>
           
                
        </table>
        
        
        </div>

<%@include file="/template/footer.jsp" %>
</div>
