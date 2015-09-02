var dataTable = function() {
    'use strict';
    return {
        url: '',
        params: '',
        num: 1, //page number start at 1
        total: 0,
        pageSize: 10,
        table: undefined,
        primaryKey: [],
        columns: [],
        isPopup: false,
        ZERO: '0 of 0',
        reset: function() {
            dataTable.url = '';
            dataTable.params = '';
            dataTable.num = 1;
            dataTable.total = 0;
            if (dataTable.table) {
                dataTable.table.find('#checkall').jqxCheckBox({checked: false});
                dataTable.table.find('tfoot .counter').html(dataTable.ZERO);
                dataTable.table.find('tbody').html('<tr><td colspan="' + (dataTable.columns.length + 2) + '" class="text-center">No record</td></tr>');
            }
        },
        reload: function() {
            if (dataTable.table) {
                dataTable.table.find('#checkall').jqxCheckBox({checked: false});
            }
            dataTable.search({
                url: dataTable.url,
                num: dataTable.num,
                params: dataTable.params
            });
        },
        init: function(table) {
            var $table = $('#' + table.id);
            if ($table.length === 0) {
                console.error('Table id ' + table.id + ' not found');
                return;
            }

            $('<div id="loader"></div>').insertBefore($table);
            $table.addClass("table table-bordered table-hover table-striped tablesorter");
            var len = table.columns.length,
                template = '<thead><tr>';
            template += '<th style="outline:0;width:5%;" class="sorter-false"><center><div id="checkall"></div></center></th>';
            for (var i = 0; i < len; i++) {
                if (!table.columns[i].sortable) {
                    template += '<th style="outline:0;width:' + table.columns[i].width + '" class="sorter-false">' +
                                table.columns[i].text +
                                '</th>';
                } else {
                    template += '<th style="outline:0;width:' + table.columns[i].width + '">' +
                                table.columns[i].text +
                                '</th>';
                }
            }
            template += '<th style="outline:0;width:10%;" class="text-center sorter-false">Action</th>';
            template += '</tr></thead>';
            var blank = '<tr><td colspan="' + (len + 2) + '" class="text-center">No record</td></tr>';
            template += '<tbody>' + blank + '</tbody>';

            var buttons =
                '<div id="btnLast" type="button" title="Last" role="button" class="pager jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" aria-disabled="false">' +
                    '<div style="margin-left:6px;width:15px;height:15px;" class="jqx-icon-arrow-last"></div>' +
                '</div>' +
                '<div id="btnNext" type="button" title="Next" role="button" class="pager jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" aria-disabled="false">' +
                    '<div style="margin-left:6px;width:15px;height:15px;" class="jqx-icon-arrow-right"></div>' +
                '</div>' +
                '<div id="btnPrevious" type="button" title="Previous" role="button" class="pager jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" aria-disabled="false">' +
                    '<div style="margin-left:6px;width:15px;height:15px;" class="jqx-icon-arrow-left"></div>' +
                '</div>' +
                '<div id="btnFirst" type="button" title="First" role="button" class="pager jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" aria-disabled="false">' +
                    '<div style="margin-left:6px;width:15px;height:15px;" class="jqx-icon-arrow-first"></div>' +
                '</div>' +
                '<div class="counter">' + dataTable.ZERO + '</div>';

            template += '<tfoot><tr><td colspan="' + (len + 2) + '" class="text-right">' +
                        buttons +
                        '</td></tr></tfoot>';

            $table.html(template);
            $table.tablesorter({
                widgets: ['zebra'],
                widgetOptions: {zebra: ['normal-row', 'alt-row']}
            });
            dataTable.table = $table;
            dataTable.primaryKey = table.hasOwnProperty('primaryKey') ? table.primaryKey : [];
            dataTable.columns = table.hasOwnProperty('columns') ? table.columns : [];
            dataTable.isPopup = table.hasOwnProperty('isPopup') ? table.isPopup : false;

            var uncheck = false,
                $checkall = $table.find('#checkall');
            if ($checkall.length > 0) {
                $checkall.jqxCheckBox({width: 15, height: 15});
                $checkall.bind('change', function (event) {
                    if (!uncheck) {
                        var checked = event.args.checked;
                        $table.find('.ticker').each(function(idx, elem) {
                            $(elem).jqxCheckBox({checked: checked});
                        });
                    }
                });
            }

            $table.on('click', 'tbody .ticker', function() {
                var checked = $(this).jqxCheckBox('checked');
                if (!checked) {
                    uncheck = true;
                    $checkall.jqxCheckBox({checked: false});
                }
                uncheck = false;
            });

            $table.on('click', 'tbody a[title="Edit"]', function(e) {
                e.preventDefault();
                if (table.hasOwnProperty('edit')) {
                    table.edit($(this).data('primary-key'));
                }
            });

            $table.on('click', 'tbody a[title="View"]', function(e) {
                e.preventDefault();
                if (table.hasOwnProperty('view')) {
                    table.view($(this).data('primary-key'));
                }
            });

            $table.on('click', 'tbody a[title="Delete"]', function(e) {
                e.preventDefault();
                if (table.hasOwnProperty('remove')) {
                    table.remove($(this).data('primary-key'));
                }
            });

            $table.on('click', 'tbody a[title="Select"]', function(e) {
                e.preventDefault();
                if (table.hasOwnProperty('select')) {
                    table.select($(this).data('primary-key'));
                }
            });

            $table.find('tfoot div[type="button"]').on('click', function() {
                if (dataTable.total > 0 &&
                    $.trim(dataTable.url).length > 0) {
                    switch($(this).attr('title')) {
                        case 'First':
                            dataTable.search({
                                url: dataTable.url,
                                num: 1,
                                params: dataTable.params
                            });
                            break;
                        case 'Previous':
                            var p = dataTable.num - 1;
                            if (p > 0) {
                                dataTable.search({
                                    url: dataTable.url,
                                    num: p,
                                    params: dataTable.params
                                });
                            }
                            break;
                        case 'Next':
                            var p = dataTable.num + 1;
                            if (p <= dataTable.total) {
                                dataTable.search({
                                    url: dataTable.url,
                                    num: p,
                                    params: dataTable.params
                                });
                            }
                            break;
                        case 'Last':
                            dataTable.search({
                                url: dataTable.url,
                                num: dataTable.total,
                                params: dataTable.params
                            });
                            break;
                        default: return;
                    }
                }
            });
        },
        search: function(info) {
            dataTable.url = info.url;
            dataTable.num = info.num;
            dataTable.params = info.params;
            $('#loader').addClass('loading');
            $.ajax({
                async: true,
                method: 'GET',
                url: info.url,
                cache: false,
                dataType: 'json',
                data: info.params + '&p=' + info.num
            })
            .done(function(json) {
                dataTable.num = json.currentPage;
                dataTable.total = json.totalPages;
                if (json.empty) {
                    dataTable.table.find('tfoot .counter').html(dataTable.ZERO);
                    dataTable.table.find('tbody').html('<tr><td colspan="' + (dataTable.columns.length + 2) + '" class="text-center">No record</td></tr>');
                } else {
                    var i = 0,
                        rows = '',
                        size = json.content.length;
                    for (; i < size; i++) {
                        var entity = json.content[i];

                        var keys = '';
                        for (var j = 0, len = dataTable.primaryKey.length; j < len; j++) {
                            if (j > 0) keys += '&';
                            keys += dataTable.primaryKey[j].name + '=' + entity[dataTable.primaryKey[j].index];
                        }

                        rows += '<tr><td><center><div class="ticker" data-primary-key="' + keys + '"></div></center></td>';
                        for (var j = 0, len = dataTable.columns.length; j < len; j++) {
                            if (dataTable.columns[j].align === 'right') {
                                rows += '<td class="text-right">';
                            } else if (dataTable.columns[j].align === 'center') {
                                rows += '<td class="text-center">';
                            } else {
                                rows += '<td class="text-left">';
                            }
                            rows += entity[dataTable.columns[j].datafield];
                            rows += '</td>';
                        }

                        rows += '<td class="text-center">';
                        if (dataTable.isPopup) {
                            rows += '<a href="javascript:void(0);" title="Select" style="font-size:12px;padding:0 10px;" data-primary-key="' + keys + '">' +
                                        '<i class="glyphicon glyphicon-ok"></i>' +
                                    '</a>';
                        } else {
                            rows += '<a href="javascript:void(0);" title="Edit" style="font-size:12px;padding:0 10px;" data-primary-key="' + keys + '">' +
                                        '<i class="glyphicon glyphicon-edit"></i>' +
                                    '</a>' +
                                    '<a href="javascript:void(0);" title="View" style="font-size:12px;padding:0 10px;" data-primary-key="' + keys + '">' +
                                        '<i class="glyphicon glyphicon-folder-open"></i>' +
                                    '</a>' +
                                    '<a href="javascript:void(0);" title="Delete" style="font-size:12px;padding:0 10px;" data-primary-key="' + keys + '">' +
                                        '<i class="glyphicon glyphicon-remove"></i>' +
                                    '</a>';
                        }
                        rows += '</td></tr>';
                    }
                    dataTable.table.find('tbody').html(rows);
                    var from = (dataTable.num - 1) * dataTable.pageSize + 1,
                        to   = from + size - 1;
                    dataTable.table.find('tfoot .counter').html(from + ' - ' + to + ' of ' + json.total);
                    dataTable.table.find('.ticker').each(function(idx, elem) {
                        $(elem).jqxCheckBox({width: 15, height: 15});
                    });
                    dataTable.table.trigger('update', [true]);//resort
                }
                $('#loader').removeClass('loading');
            })
            .fail(function(xhr, textStatus, errorThrown) {
                $('#loader').removeClass('loading');
                alert(errorThrown);
            });
        }
    };
}();