<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><a href="pages/applications">My applications</a> > ${application.name}</h1>
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
        <div class="col-lg-12">
            <div class="panel panel-default">
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form id="form" role="form" method="post">
                        <div class="form-group">
                            <label>ID</label>
                            <input name="appId" value="${application.id}" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label>Created date</label>
                            <input name="appCreatedDate" value="${application.createdDate}" type="datetime-local"
                                   class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label>Name</label>
                            <input id="app-name-input" name="appName" value="<c:out value="${application.name}"/>" class="form-control"
                                   placeholder="Enter name" required>
                        </div>
                        <div class="form-group">
                            <label>Description</label>
                            <input id="app-description-input" name="appDescription" value="<c:out value="${application.description}"/>" class="form-control"
                                   placeholder="Enter description">
                        </div>
                        <div class="form-group">
                            <label><code>API_KEY</code></label>
                            <input name="appApiKey" value="${application.apiKey}" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label><code>API_SECRET</code></label>
                            <input name="appApiSecret" value="${application.apiSecret}" class="form-control"
                                   readonly="">
                        </div>
                        <button id="update-app" name="update" type="submit" class="btn btn-default btn-block">Save</button>
                        <button id="delete-app" name="delete" class="btn btn-default btn-block">Delete</button>
                    </form>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        $("button[name=update]").click(function () {
            $("#form").attr("action", "pages/applications?action=update");
            $("#form").submit();
        });

        $("button[name=delete]").click(function () {
            $("#form").attr("action", "pages/applications?action=delete");
            $("#form").submit();
        });
    });
</script>

<%@include file="includes/footer.jsp" %>
