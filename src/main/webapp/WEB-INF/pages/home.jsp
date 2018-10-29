<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Welcome to the demo app!</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <c:if test="${error != null}">
        <div class="alert alert-dismissable ${error.cssClass}">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            ${error.message}
        </div>
        </c:if>
        <div class="alert alert-info" role="alert">
            You are logged in as ${principal}.
        </div>
    </div>
    <!-- /.row -->
</div>

<%@include file="includes/footer.jsp" %>
