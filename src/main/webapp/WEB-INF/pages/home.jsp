<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Home page</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <c:if test="${alert != null}">
        <div class="col-lg-12">
            <div class="alert alert-dismissable ${alert.cssClass}">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    ${alert.message}
            </div>
        </div>
        </c:if>
        <c:if test="${principal.role == 'ADMINISTRATOR'}">
        <div class="col-lg-3 col-md-6">
            <div class="panel panel-green">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3">
                            <i class="fa fa-users fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                            <div class="huge">${userNumbers}</div>
                            <div>users</div>
                        </div>
                    </div>
                </div>
                <a href="pages/users">
                    <div class="panel-footer">
                        <span class="pull-left">View users</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
        </c:if>
        <div class="col-lg-3 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3">
                            <i class="fa fa-gamepad fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                            <div class="huge">${appNumbers}</div>
                            <div>applications</div>
                        </div>
                    </div>
                </div>
                <a href="pages/applications">
                    <div class="panel-footer">
                        <span class="pull-left">View my applications</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
    </div>
    <!-- /.row -->
</div>

<%@include file="includes/footer.jsp" %>
