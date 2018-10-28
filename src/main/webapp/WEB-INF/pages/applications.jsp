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
            "responsive": true,
            //"ajax": "api/applications",
            "columns": [
                { "data": "name" },
                { "data": "description" },
                { "data": "apiKey" },
                { "data": "apiSecret" },
            ],
            "columnDefs": [
                {
                    "targets": -1,
                    "data": null,
                    "defaultContent": "<button>Click!</button>"
                }
            ],
            "language": {
                "emptyTable": "No data available in table"
            }
        });
    });
</script>

<%@include file="includes/footer.jsp" %>
