
function callTrack(param){
    $("#"+param.containerId).empty();
    $("#"+param.containerId).append("<table id=\"table-posttracking"+param.containerId+"\" class=\"table table-striped table-bordered\" cellspacing=\"0\" style=\"width:100%;\"></table>");
    var posttracking_table;
    if ($.fn.dataTable.isDataTable('#table-posttracking'+param.containerId)) {
        posttracking_table = $('#table-posttracking'+param.containerId).DataTable();
        posttracking_table.destroy();
    }
    posttracking_table = $('#table-posttracking'+param.containerId).DataTable({
        "ajax": {
            "url": "https://api.ee.express/hydracargo/posttracking/gettable",
            "type": "get",
            "dataType": "json",
            "data": { "shipment": param.shipment }
        },
        "processing" : true,
        "searching" : false,
        "ordering": false,
        "select" : {
            style: 'single'
        },
        "bInfo": false,
        "columns": [
            {"width": "20%", "title": "shipment", "data": "trc_shipment"},
            {"width": "20%", "title": "tracnum", "data": "trc_tracnum"},
            {"width": "40%", "title": "event", "data": "trc_event"},
            {"width": "20%", "title": "eventtime", "data": "trc_eventtime"},

        ]
    });
}