<%-- 
This is a very basic JSP page. Notice that we can include other JSPs (so that we
can use the header and the footer across different pages). In this case, it is
a static include, which means that the header and footers will be included when
the JSP is compiled into a servlet.

The servlet (controller) that received the initial HTTP request and delegated
rendering of the view to this JSP did not send us any model. We only display
static content.
--%>

<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">About</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <p>This is just a demo application. Don't expect much.</p>

        <div class="alert alert-info" role="alert">
            By the way, this page only contains static content (no model provided by the
            servlet and used by the view).
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<%@include file="includes/footer.jsp" %>
