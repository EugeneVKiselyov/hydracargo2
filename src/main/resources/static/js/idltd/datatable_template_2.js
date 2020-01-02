function recreateDataTable2(vinput_values) {

    var vtable = $(vinput_values.table);
    var vdata_table;

    var vtable_dom = vinput_values.dom === undefined ? 'frtip' : vinput_values.dom;
    /*  l - length changing input control
        f - filtering input
        t - The table!
        i - Table information summary
        p - pagination control
        r - processing display element
        B - Buttons
        S - Select  */

    if ($.fn.dataTable.isDataTable($(vinput_values.table))) {
        $(vinput_values.table).DataTable().destroy();
    }

    datatable_option = {
        // "ajax": vinput_values.ajax,
        // "serverSide" : true,
        "ajax" : function ( data, callback, settings ) {

            var jsonData = JSON.parse(JSON.stringify(vinput_values.ajax,function(key, value) {
                if (key == 'data') {
                    if (typeof value === "function") {
                        var d={};
                        value(d);
                        return d;
                    } else {
                        return value;
                    }
                } else { return value; }
            }));

            function afterReload( donedata ){
                var rowIndexes = [];
                $(vinput_values.table).DataTable().rows( function ( idx, data, node ) {
                    if(data[vinput_values.key] === vinput_values.rowdata[vinput_values.key]){
                        rowIndexes.push(idx);
                    }
                    return false;
                });
                $(vinput_values.table).DataTable().rows(rowIndexes).select();
                if (typeof vinput_values.switchControl !== 'undefined' ){ vinput_values.switchControl(); }
            };

            $.ajax(jsonData).done(function( donedata ) {
                callback( donedata );
                afterReload( donedata );
            });

            //этот костыль надо убрать
            setTimeout(function() {
                $(vinput_values.table).DataTable().columns.adjust().draw();
            }, 300);

        },
        "initComplete": function(settings, json) {

            if (typeof vinput_values.initComplete !== 'undefined' ){
                vinput_values.initComplete(vdata_table);
            }
            if (vinput_values.columnSearch){
                this.api().columns(vinput_values.columnSearchSelect).every( function () {
                    var column = this;
                    var select = $('<select><option value=""></option></select>')
                        .appendTo( $(column.footer()).empty() )
                        .on( 'change', function () {
                            var val = $.fn.dataTable.util.escapeRegex($(this).val());
                            if(val == "null") {
                                column.search('^$',true,false).draw();
                            }
                            else {
                                column.search(val ? '^' + val + '$' : '', true, false).draw();
                            }
                        });
                    column.data().unique().sort().each( function ( d, j ) {
                        select.addClass("form-control").append('<option value="' + d + '">' + d + '  </option>')
                    });
                });
            }
        },
        "orderCellsTop": typeof vinput_values.orderCellsTop === 'undefined' ? true : vinput_values.orderCellsTop ,
        "processing" : typeof vinput_values.processing === 'undefined' ? true : vinput_values.processing ,
        "searching" : typeof vinput_values.searching === 'undefined' ? true : vinput_values.searching ,
        "ordering": vinput_values.ordering,
        "order": vinput_values.order,
        "scrollY" : vinput_values.scrollY,
        "scrollX": vinput_values.scrollX,
        "fixedHeader": vinput_values.fixedHeader,
        "scrollCollapse": typeof vinput_values.scrollCollapse === 'undefined' ? true : vinput_values.scrollCollapse ,
        "scroller": typeof vinput_values.scroller === 'undefined' ? true : vinput_values.scroller ,
        "select" : {
            style: 'single'
        },
        "LengthChange": typeof vinput_values.LengthChange === 'undefined' ? true : vinput_values.LengthChange ,
        "lengthMenu": [
            [5, 10, 25, 50, 100, -1],
            [5, 10, 25, 50, 100, "All"]
        ],
        "pageLength": vinput_values.pageLength,
        "paging": typeof vinput_values.paging === 'undefined' ? true : vinput_values.paging ,
        "responsive": typeof vinput_values.responsive === 'undefined' ? true : vinput_values.responsive,
        "createdRow": vinput_values.createdRow,
        "language": {
            "sEmptyTable":     /*[[#{datatable.sEmptyTable}]]*/ "No data available in table",
            "sInfo":           /*[[#{datatable.sInfo}]]*/ "Showing _START_ to _END_ of _TOTAL_ entries",
            "sInfoEmpty":      /*[[#{datatable.sInfoEmpty}]]*/ "Showing 0 to 0 of 0 entries",
            "sInfoFiltered":   /*[[#{datatable.sInfoFiltered}]]*/ "(filtered from _MAX_ total entries)",
            "sInfoPostFix":    /*[[#{datatable.sInfoPostFix}]]*/ "",
            "sInfoThousands":  /*[[#{datatable.sInfoThousands}]]*/ ",",
            "sLengthMenu":     /*[[#{datatable.sLengthMenu}]]*/ "Show _MENU_ entries",
            "sLoadingRecords": /*[[#{datatable.sLoadingRecords}]]*/ "Loading...",
            "sProcessing":     /*[[#{datatable.sProcessing}]]*/ "Processing...",
            "sSearch":         /*[[#{datatable.sSearch}]]*/ "Search:",
            "sZeroRecords":    /*[[#{datatable.sZeroRecords}]]*/ "No matching records found",
            "oPaginate": {
                "sFirst":    /*[[#{datatable.oPaginate.sFirst}]]*/ "First",
                "sLast":     /*[[#{datatable.oPaginate.sLast}]]*/ "Last",
                "sNext":     /*[[#{datatable.oPaginate.sNext}]]*/ "Next",
                "sPrevious": /*[[#{datatable.oPaginate.sPrevious}]]*/ "Previous"
            },
            "oAria": {
                "sSortAscending":  /*[[#{datatable.oAria.sSortAscending}]]*/ ": activate to sort column ascending",
                "sSortDescending": /*[[#{datatable.oAria.sSortDescending}]]*/ ": activate to sort column descending"
            }
        },
        "columns": vinput_values.columns,
        "columnDefs": vinput_values.columnDefs,
        fixedColumns : vinput_values.fixedColumns,
        dom: vtable_dom,
        buttons: [
            {extend: 'copy', text: '<u>C</u>opy', key: { key: 'c', altKey: true}, className: 'copyButton',
                exportOptions: { columns: ':visible'}
            },
            {extend: 'excel', text: 'E<u>x</u>cel', key: { key: 'x', altKey: true}, className: 'excelButton'},
            {extend: 'pdf', text: '<u>P</u>df', key: { key: 'p', altKey: true}, className: 'pdfButton',
                exportOptions: { columns: ':visible'},
                customize : function(doc){
                    var colCount = new Array();
                    vtable.find('tbody tr:first-child td').each(function(){
                        if($(this).attr('colspan')){
                            for(var i=1;i<=$(this).attr('colspan');$i++){
                                colCount.push('*');
                            }
                        }else{ colCount.push('*'); }
                    });
                    doc.content[1].table.widths = colCount;
                }
            },
            {extend: 'print', text: 'P<u>r</u>int', key: { key: 'r', altKey: true}, className: 'printButton',
                exportOptions: { columns: ':visible'}
            }
        ]
    }

    vdata_table = $(vinput_values.table).DataTable(datatable_option);


    $('.copyButton').removeClass('btn-secondary').addClass('btn-primary');
    $('.excelButton').removeClass('btn-secondary').addClass('btn-primary');
    $('.pdfButton').removeClass('btn-secondary').addClass('btn-primary');
    $('.printButton').removeClass('btn-secondary').addClass('btn-primary');

    if(!vinput_values.copy_button) { vdata_table.buttons( '.copyButton' ).remove();}
    if(!vinput_values.excel_button) { vdata_table.buttons( '.excelButton' ).remove();}
    if(!vinput_values.pdf_button) { vdata_table.buttons( '.pdfButton' ).remove();}
    if(!vinput_values.print_button) { vdata_table.buttons( '.printButton' ).remove();}

    vdata_table
        .off('select')
        .off('deselect')
        .on( 'select', function ( e, dt, type, indexes ) {
            var rowData = vdata_table.row( indexes ).data();
            Object.keys(vinput_values.rowdata).forEach(function(index) {
                vinput_values.rowdata[index] = rowData[index]
            });
            if (typeof vinput_values.switchControl !== 'undefined' ){ vinput_values.switchControl(); }
        })
        .on( 'deselect', function (e, dt, type, indexes) {
            Object.keys(vinput_values.rowdata).forEach(function(index) {
                vinput_values.rowdata[index] = null
            });
            if (typeof vinput_values.switchControl !== 'undefined' ){ vinput_values.switchControl(); }
        });
}