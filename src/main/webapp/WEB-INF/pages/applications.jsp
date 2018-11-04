<%@include file="includes/header.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">My applications</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a name="collapse-title" data-toggle="collapse" data-parent="#accordion" href="#collapse" aria-expanded="true"
                           class="">Create a new application</a>
                    </h4>
                </div>
                <div id="collapse" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                    <div class="panel-body">
                        <form role="form" method="post">
                            <div class="form-group">
                                <label>Name</label>
                                <input name="appName" value="${appName}" class="form-control" placeholder="Enter name">
                            </div>
                            <div class="form-group">
                                <label>Description</label>
                                <input name="appDescription" value="${appDescription}" class="form-control" placeholder="Enter description">
                            </div>
                            <button name="submit" type="submit" class="btn btn-default btn-block">Add</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    Applications
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="applications">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th><code>API_KEY</code></th>
                            <th><code>API_SECRET</code></th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
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
        $('#applications').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "api/applications",
                "method": "GET",
            },
            "columns": [
                { "data": "name" },
                { "data": "description" },
                { "data": "apiKey" },
                { "data": "apiSecret" },
                { "data": "id" }
            ],
            "columnDefs": [
                {
                    "targets": 2,
                    "data": "id",
                    "render": function(data) {
                        return '<code>' + data + '</code>';
                    }
                },
                {
                    "targets": 3,
                    "data": "id",
                    "render": function(data) {
                        return '<code>' + data + '</code>';
                    }
                },
                {
                    "targets": -1,
                    "render": function(data) {
                        actions = '<a href="pages/applications?appId=' + data + '" class="btn btn-default"><i class="fa fa-eye"></a>';

                        return actions;
                    }
                }
            ],
            "language": {
                "emptyTable": "No application in table.",
                "zeroRecords": "There were no matching applications found."
            },
            "searching": false,
            "ordering": false,
            "paging": true,
            "responsive": true
        });
    });
</script>

<%@include file="includes/footer.jsp" %>
