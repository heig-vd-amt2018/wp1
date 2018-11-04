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
        <div class="col-lg-12">
            <div class="panel panel-default">
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form id="form" role="form">
                        <div class="form-group">
                            <label>ID</label>
                            <input name="appId" value="${application.id}" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label>Created date</label>
                            <input name="appCreatedDate" value="${application.createdDate}" type="datetime-local" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label>Name</label>
                            <input name="appName" value="${application.name}" class="form-control" placeholder="Enter name">
                        </div>
                        <div class="form-group">
                            <label>Description</label>
                            <input name="appDescription" value="${application.description}" class="form-control" placeholder="Enter description">
                        </div>
                        <div class="form-group">
                            <label><code>API_KEY</code></label>
                            <input name="appApiKey" value="${application.apiKey}" class="form-control" readonly="">
                        </div>
                        <div class="form-group">
                            <label><code>API_SECRET</code></label>
                            <input name="appApiSecret" value="${application.apiSecret}" class="form-control" readonly="">
                        </div>
                        <button name="save" class="btn btn-default btn-block">Save</button>
                        <button name="delete" class="btn btn-default btn-block">Delete</button>
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
    document.addEventListener("DOMContentLoaded", function() {
        $("button[name=save]").click(function (e) {
            $.ajax({
                method: "POST",
                url: "pages/applications?" + $("#form").serialize(),
                complete: function () {
                    window.location.href = "pages/applications";
                }
            });

            e.preventDefault();
            return false;
        });

        $("button[name=delete]").click(function (e) {
            $.ajax({
                method: "DELETE",
                url: "pages/applications?" + $("#form").serialize(),
                complete: function () {
                    window.location.href = "pages/applications";
                }
            });

            e.preventDefault();
            return false;
        });
    });
</script>

<%@include file="includes/footer.jsp" %>
