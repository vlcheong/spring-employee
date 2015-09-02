<%@page autoFlush="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="resources" value="${pageContext.request.contextPath}/resources" />
<div class="panel panel-primary">
    <div class="panel-heading">Search Criteria</div>
    <div class="panel-body">
        <form id="searchForm" class="form-horizontal">
            <div class="form-group">
                <label for="firstName" class="col-sm-2 control-label">First Name</label>
                <div class="col-sm-2">
                    <input type="text" name="firstName" id="firstName" class="form-control" placeholder="First Name">
                </div>
                <div class="col-sm-1" style="padding-top:5px;">
                    <div id="inclFirstName" data-name="inclFirstName" data-value="Y"><label>Include</label></div>
                </div>
                <label for="lastName" class="col-sm-2 control-label">Last Name</label>
                <div class="col-sm-2">
                    <input type="text" name="lastName" id="lastName" class="form-control" placeholder="Contact Name">
                </div>
                <div class="col-sm-1" style="padding-top:5px;">
                    <div id="inclLastName" data-name="inclLastName" data-value="Y"><label>Include</label></div>
                </div>
            </div>

            <div class="form-group">
                <label for="email" class="col-sm-2 control-label">Email</label>
                <div class="col-sm-3">
                    <input type="email" name="email" id="email" class="form-control" placeholder="Email">
                </div>
                <label for="gender" class="col-sm-2 control-label">Gender</label>
                <div class="col-sm-1" style="padding-top:5px;">
                    <div data-name="gender" data-value=""><label>All</label></div>
                </div>
                <div class="col-sm-1" style="padding-top:5px;">
                    <div data-name="gender" data-value="M"><label>Male</label></div>
                </div>
                <div class="col-sm-2" style="padding-top:5px;">
                    <div data-name="gender" data-value="F"><label>Female</label></div>
                </div>
            </div>

            <div class="form-group">
                <label for="company" class="col-sm-2 control-label">Company</label>
                <div class="col-sm-3">
                    <select name="company" id="company" class="form-control" placeholder="Company" style="height:35px;">
                        <option value="0"></option>
                        <c:forEach var="company" items="${companies}">
                            <option value="${company.id}">${company.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <label for="hireDate" class="col-sm-2 control-label">Hire Date</label>
                <div class="col-sm-2">
                    <div id="fromHireDate" data-name="fromHireDate"></div>
                </div>
                <label class="col-sm-1 text-center">To</label>
                <div class="col-sm-2">
                    <div id="toHireDate" data-name="toHireDate"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-8 col-sm-4 text-right">
                    <button type="reset" id="btnReset" class="btn btn-default">
                        <i class="glyphicon glyphicon-refresh"></i>&nbsp;Reset
                    </button>
                    <button type="button" id="btnSearch" class="btn btn-default">
                        <i class="glyphicon glyphicon-search"></i>&nbsp;Search
                    </button>
                </div>
            </div>
        </form>
    </div>
    <div style="padding:0 3px;">
        <table id="result"></table>
    </div>
    <div style="padding:3px;" class="text-right">
        <button type="button" id="btnAdd" class="btn btn-default">
            <i class="glyphicon glyphicon-plus"></i>&nbsp;Add
        </button>
        <button type="button" id="btnDelete" class="btn btn-default">
            <i class="glyphicon glyphicon-trash"></i>&nbsp;Delete
        </button>
    </div>
</div>
<script src="${resources}/common/js/theme.js"></script>
<script src="${resources}/common/js/datatable.js"></script>
<script src="${resources}/common/js/dateTimeInput.js"></script>
<script>
$(function() {
    'use strict';
    $('#searchForm').find('div[data-name="gender"]').each(function(idx, elem) {
        var checked = false;
        if ($(elem).data('value') === '') {
            checked = true;
        }
        $(elem).jqxCheckBox({groupName: 'gender', width: 15, height: 15, checked: checked});
    });
    $('#searchForm #inclFirstName').jqxCheckBox({width: 15, height: 15, checked: false});
    $('#searchForm #inclLastName').jqxCheckBox({width: 15, height: 15, checked: false});

    $('#fromHireDate').dateTimeInput({width: '160px', value: null});
    $('#toHireDate').dateTimeInput({width: '160px', value: null});

    $('#btnSearch').jqxButton({template: 'primary'});
    $('#btnAdd').jqxButton({template: 'success'});

    var columns = [{
        text: 'First Name',
        datafield: '1',
        columntype: 'string',
        width: '15%',
        align: 'left',
        sortable: true
    }, {
        text: 'Last Name',
        datafield: '2',
        columntype: 'string',
        width: '15%',
        align: 'left',
        sortable: true
    }, {
        text: 'Gender',
        datafield: '3',
        columntype: 'string',
        width: '10%',
        sortable: true,
        align: 'center'
    }, {
        text: 'Birth Date',
        datafield: '4',
        columntype: 'date',
        width: '10%',
        sortable: true,
        align: 'left'
    }, {
        text: 'Mobile',
        datafield: '5',
        columntype: 'string',
        width: '10%',
        sortable: true,
        align: 'left'
    }, {
        text: 'Email',
        datafield: '6',
        columntype: 'string',
        width: '15%',
        sortable: true,
        align: 'left'
    }];

    dataTable.init({
        id: 'result',
        primaryKey: [{index: '0', name: 'id'}],
        columns: columns,
        isPopup: false,
        edit: function(keys) {
            ajax.get({
                url: '${context}/employee/form',
                dataType: 'html',
                data: keys,
                callback: function(html) {
                    content.secondary(html).toggle();
                },
                error: function(xhr, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        },
        view: function(keys) {
            ajax.get({
                url: '${context}/employee/view',
                dataType: 'html',
                data: keys,
                callback: function(html) {
                    if (!content.isError(html)) {
                        content.secondary(html).toggle();
                    }
                },
                error: function(xhr, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        },
        remove: function(keys) {
            ajax.post({
                url: '${context}/employee/delete',
                data: keys,
                callback: function(json) {
                    alert(json.messages.join('\n'));
                    dataTable.reload();
                },
                error: function(xhr, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        },
        select: function(keys) {
            console.log(keys);
        }
    });

    $('#searchForm').on('click', '#btnSearch', function(e) {
        e.preventDefault();
        dataTable.search({
            url: '${context}/employee/search',
            num: 1,
            params: inputForm.serialize('searchForm')
        });
    });

    $('#searchForm').on('click', '#btnReset', function(e) {
        dataTable.reset();
    });

    $('#btnAdd').on('click', function(e) {
        e.preventDefault();
        ajax.get({
            url: '${context}/employee/form',
            dataType: 'html',
            callback: function(html) {
                content.secondary(html).toggle();
            },
            error: function(xhr, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    });

    $('#btnDelete').on('click', function(e) {
        e.preventDefault();
        var i = 0,
            keys = '';
        $('table#result').find('div.ticker[role="checkbox"]').each(function(idx, elem) {
            if ($(elem).attr('aria-checked') === 'true') {
                if (i > 0) keys += '&';
                keys += $(elem).data('primary-key');
                i++;
            }
        });
        if (i === 0) {
            alert('Please select at least one');
        } else {
            if (confirm('Are you sure ?')) {
                ajax.post({
                    url: '${context}/employee/batchDelete',
                    data: keys,
                    callback: function(json) {
                        alert(json.messages.join('\n'));
                        dataTable.reload();
                    },
                    error: function(xhr, textStatus, errorThrown) {
                        alert(errorThrown);
                    }
                });
            }
        }
    });
});
</script>