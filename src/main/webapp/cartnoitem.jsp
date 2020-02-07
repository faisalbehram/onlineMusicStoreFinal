<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>
<%@include file="/template/header.jsp" %>

<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>

<body>
<div class="container-wrapper">
    <div class="container">
        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1>Cart</h1>

                    <p>All the selected products in your shopping cart</p>
                </div>
            </div>
        </section>

        <section class="container">
            <div >
            <div>
            <h3>${msg}</h3>
            </div></div></section></div></div>   
</body>
</html>

<%@include file="/template/footer.jsp" %>
<%-- 
<td>    <c:forEach  items="${accessories}" var="accessory" >
              ${accessory.accessoryName} / ${accessory.cartItem.iterator().next().cartItemId} //${item.cartItem.iterator().next().cartItemId }
                <c:if test="${accessory.products.iterator().next().productId == item.product.productId}">
                    	
                     <input type="checkbox" name ="${accessory.accessoryId}"> ${accessory.accessoryName}
                       </c:if>
                   
                    </c:forEach>
                    </td> --%>