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
                        <strong>Base Price</strong>  <strike>${pricesWithDiscount.basicPrice} </strike>USD </h4>
                     
                     <h4>
                     	Discount Percentage : ${pricesWithDiscount.discountPercentage}
                    </h4>
                   			 
                   			   <h4><strong>Discount Price</strong> ${pricesWithDiscount.discountPrice}
                   			  
                   			   	</h4>
                   			   	 <h4>   <strong>Tax Percentage	</strong>  ${product.price2.get(0).percentageDiscount * 100}  </h4>
                     			 <h4>  <strong>Total Price</strong>  ${product.price2.get(0).price}  </h4>
                   			                				 
                   		 
                    <br>
                    <h4>Accessories</h4>
                    <form action="/cart/add/${pageContext.request.userPrincipal.name}/${product.productId}">
                    <h5>
                    <c:forEach  items="${accessories}" var="accessory" >
                   
                     <input type="checkbox" name ="accessoryId" value="${accessory.accessoryId}"> ${accessory.accessoryName}
                   		  <strong>Price </strong> ${accessory.price2.get(0).price	}
                  
                     <br>
                 
                    </c:forEach>
                   	</h5>
                   	   <c:if test="${pageContext.request.userPrincipal.name  != null}">    
                      <%--   <a href="/cart/add/${pageContext.request.userPrincipal.name}/${product.productId}" class="btn btn-warning btn-large">
                        <span class="glyphicon glyphicon-shopping-cart"></span>Order Now</a> --%>
                       
                       <input type="submit" value="order" class="btn btn-warning btn-large" >
                     
                        <a href="<spring:url value="/customer/cart/${pageContext.request.userPrincipal.name}" />"
                           class="btn btn-default"><span class="glyphicon glyphicon-hand-right"></span>View Cart</a>
                  
                    </c:if>
                      </form>
                    <c:set var="role" scope="page" value="${param.role}" />
                    <c:set var="url" scope="page" value="/productList" />
                    <c:if test="${role='admin'}">
                        <c:set var="url" scope="page" value="/admin/productInventory" />
                    </c:if>
					
					
                    <p>
                        <a href="<c:url value="${url}" />" class="btn btn-default">Back</a>
                        <c:if test="${pageContext.request.userPrincipal.name  == null}">
                        <p><b>TO Order Please <a href="/login"> Login First</a></b></p>
                     </c:if>
                 
                </div>
            </div>
        </div>


     
        <%@include file="/template/footer.jsp" %>
</div>
</div>
</body>
</html>
