<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template/header.jsp"%>

<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>

<body>
<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Product Detail</h1>

            <p class="lead">Here is the detail information of the product!</p>
        </div>

        <div class="container" >
            <div class="row">
                <div class="col-md-5">
                    <img src="<c:url value="/resources/images/${product.productId}.png" /> " alt="image"
                             style="width:100%"/>
                </div>

                <div class="col-md-5">
                    <h3>${product.productName}</h3>
                    <p>${product.productDescription}</p>
                    <p>
                       <strong>Manufacturer</strong> : ${product.productManufacturer}
                    </p>
                    
                    <p>
                        <strong>Category</strong> :${subCategories.categoryName} > ${mainCategories.categoryName}
                        
                    </p>
                    <p>
                        <strong>Condition</strong> : ${product.productCondition}
                    </p>
                    
                    <h4>
                        <strong> Price</strong>  <strike>${discountPrice.basicPrice} </strike>USD 
                    </h4>
                    
                     <h4>
                     	<c:choose>
                     		<c:when test="${discountPrice.discountPercentage != 0}">
                     				 <strong> Discount Percentage</strong>:::  ${discountPrice.discountPercentage} % 
                     		</c:when>
                     		<c:otherwise>
                     		 <c:forEach items="${allCategoryDiscounts}" var="CategoryDiscount">
           						
           						<c:if test ="${CategoryDiscount.category.id == subCategories.id || CategoryDiscount.category.id == mainCategories.id}" >
           									
           							Category Discount ::: ${CategoryDiscount.percentageDiscount} %
           									</c:if>
           									
           						</c:forEach>
                     		</c:otherwise>
                     	</c:choose>
                       
                    </h4>
                   			 
                   			   <h4><strong>Discount Price</strong>
                   			   	<c:choose>
                   			   	<c:when test="${discountPrice.discountPrice != 0}">
                   			   	${discountPrice.discountPrice} USD
                   			   	</c:when>
                   			   	<c:otherwise>
                   			   	
                   			   	 <c:forEach items="${categoryDiscounts}" var="categoryDiscount">
                   			   	 			<%-- ${categoryDiscount.categoryId.id } ${product.category.id } ${ product.category.parentId} ${ categoryDiscount.proId.productId} 
                   			   	 			${ product.productId} --%>
                   			   	 			<br>
                   			   	 			
                   			   <c:if test="${categoryDiscount.categoryId.id  == product.category.id || categoryDiscount.categoryId.id  ==  product.category.parentId && categoryDiscount.proId.productId == product.productId }">
                   			   ${ categoryDiscount.discountPrice}
                   			   </c:if>
                   			   </c:forEach>
                   			   	</c:otherwise>
                   			   	
                   			   	
                   			   	</c:choose>
                   			   	</h4>
                   			                				 
                   		 
                    <br>

                    <c:set var="role" scope="page" value="${param.role}" />
                    <c:set var="url" scope="page" value="/productList" />
                    <c:if test="${role='admin'}">
                        <c:set var="url" scope="page" value="/admin/productInventory" />
                    </c:if>
					
					
                    <p >
                        <a href="<c:url value="${url}" />" class="btn btn-default">Back</a>
                        <c:if test="${pageContext.request.userPrincipal.name  == null}">
                        <p><b>TO Order Please <a href="/login"> Login First</a></b></p>
                     </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name  != null}">    
                        <a href="/cart/add/${pageContext.request.userPrincipal.name}/${product.productId}" class="btn btn-warning btn-large">
                        <span class="glyphicon glyphicon-shopping-cart"></span>Order Now</a>
                        
                        <a href="<spring:url value="/customer/cart/${pageContext.request.userPrincipal.name}" />"
                           class="btn btn-default"><span class="glyphicon glyphicon-hand-right"></span>View Cart</a>
                    </p>
                    </c:if>
                </div>
            </div>
        </div>


     
        <%@include file="/template/footer.jsp" %>
</div>
</div>
</body>
</html>
