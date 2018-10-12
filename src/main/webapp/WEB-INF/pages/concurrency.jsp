<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Concurrency demo</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="alert alert-info" role="alert">
            This page has been accessed ${numberOfRequests} times. Or ${numberOfRequests2}?
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<%@include file="includes/footer.jsp" %>
