<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">My profile</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <form role="form">
            <div class="form-group">
                <label>First name</label>
                <p class="form-control-static">${principal.firstName}</p>
            </div>
            <div class="form-group">
                <label>Last name</label>
                <p class="form-control-static">${principal.lastName}</p>
            </div>
            <div class="form-group">
                <label>Email</label>
                <p class="form-control-static">${principal.email}</p>
            </div>
        </form>
    </div>
    <!-- /.row -->
</div>

<%@include file="includes/footer.jsp" %>
