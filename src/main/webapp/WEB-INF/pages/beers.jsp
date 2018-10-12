<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Beers</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <ul>
            <c:forEach items="${beers}" var="beer">
                <li>${beer.name}, ${beer.brewery}, ${beer.country}, ${beer.style}</li>
            </c:forEach>
        </ul>
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<%@include file="includes/footer.jsp" %>
