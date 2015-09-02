<%@ page autoFlush="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="resources" value="${pageContext.request.contextPath}/resources" />
<c:if test="${action == 'View'}">
    <c:set var="view" value="readonly"/>
</c:if>
<div class="panel panel-primary">
    <div class="panel-heading">${action} Employee</div>
    <div class="panel-body">
        <form id="employeeForm" class="form-horizontal">
            <input type="hidden" name="id" value="${employee.id}">
            <div class="form-group">
                <label for="firstName" class="col-sm-2 control-label required">First Name</label>
                <div class="col-sm-4">
                    <input type="text" name="firstName" id="firstName" maxlength="50" class="form-control" placeholder="First Name" value="${employee.firstName}" ${view}>
                </div>
                <label for="lastName" class="col-sm-2 control-label">Last Name</label>
                <div class="col-sm-4">
                    <input type="text" name="lastName" id="lastName" maxlength="50" class="form-control" placeholder="Last Name" value="${employee.lastName}" ${view}>
                </div>
            </div>

            <div class="form-group">
                <label for="birthDate" class="col-sm-2 control-label required">Birth Date</label>
                <div class="col-sm-4">
                    <div id="birthDate" data-name="birthDate"></div>
                </div>
                <label for="gender" class="col-sm-2 control-label required">Gender</label>
                <div class="col-sm-2">
                    <div data-name="gender" data-value="F"><label>Female</label></div>
                </div>
                <div class="col-sm-2">
                    <div data-name="gender" data-value="M"><label>Male</label></div>
                </div>
            </div>

            <div class="form-group">
                <label for="email" class="col-sm-2 control-label required">Email</label>
                <div class="col-sm-4">
                    <input type="email" name="email" id="email" maxlength="50" class="form-control" placeholder="Email" value="${employee.email}" ${view}>
                </div>
                <label for="company" class="col-sm-2 control-label required">Company</label>
                <div class="col-sm-4">
                    <select name="company.id" id="company" class="form-control" placeholder="Company" style="height:35px;" ${view}>
                        <c:forEach var="company" items="${companies}">
                            <option value="${company.id}" <c:if test="${company.id == employee.company.id}">selected</c:if>>${company.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label required">Mobile</label>
                <div class="col-sm-4">
                    <input type="text" name="mobile" id="mobile" maxlength="30" class="form-control" placeholder="Mobile" value="${employee.mobile}" ${view}>
                </div>
                <label for="homePhone" class="col-sm-2 control-label">Home</label>
                <div class="col-sm-4">
                    <input type="text" name="homePhone" id="homePhone" maxlength="30" class="form-control" placeholder="Home Phone" value="${employee.homePhone}" ${view}>
                </div>
            </div>

            <div class="form-group">
                <label for="street" class="col-sm-2 control-label required">Street</label>
                <div class="col-sm-4">
                    <input type="text" name="address.street" id="street" maxlength="255" class="form-control" placeholder="Street" value="${employee.address.street}" ${view}>
                </div>
                <label for="city" class="col-sm-2 control-label required">City</label>
                <div class="col-sm-4">
                    <input type="text" name="address.city" id="city" maxlength="50" class="form-control" placeholder="City" value="${employee.address.city}" ${view}>
                </div>
            </div>

            <div class="form-group">
                <label for="zip" class="col-sm-2 control-label required">Zip</label>
                <div class="col-sm-4">
                    <input type="text" name="address.zip" id="zip" maxlength="10" class="form-control" placeholder="Zip" value="${employee.address.zip}" ${view}>
                </div>
                <label for="state" class="col-sm-2 control-label">State</label>
                <div class="col-sm-4">
                    <input type="text" name="address.state" id="state" maxlength="30" class="form-control" placeholder="State" value="${employee.address.state}" ${view}>
                </div>
            </div>

            <div class="form-group">
                <c:if test="${action != 'New'}">
                    <label for="hireDate" class="col-sm-2 control-label">Hire Date</label>
                    <div class="col-sm-4">
                        <input type="text" name="hireDate" id="hireDate" class="form-control" value="${employee.hireDate}" readonly>
                    </div>
                </c:if>
                <label for="description" class="col-sm-2 control-label">Description</label>
                <div class="col-sm-4">
                    <textarea name="description" id="description" maxlength="255" ${view}>${employee.description}</textarea>
                </div>
            </div>
            <hr>
            <div class="form-group">
                <div class="col-sm-offset-8 col-sm-4 text-right">
                    <button type="button" id="btnCancel" class="btn btn-default">
                        <i class="glyphicon glyphicon-remove-sign"></i>&nbsp;Cancel
                    </button>
                    <c:if test="${action != 'View'}">
                        <button type="button" id="btnSave" class="btn btn-default">
                            <i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;Save
                        </button>
                    </c:if>
                </div>
            </div>

        </form>
    </div>
</div>
<script src="${resources}/common/js/theme.js"></script>
<script src="${resources}/common/js/dateTimeInput.js"></script>
<script>
$(function() {
    $('#employeeForm #birthDate').dateTimeInput({
        value: null,
        disabled: <c:choose><c:when test="${action == 'View'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>
    });

    $('#employeeForm').find('div[data-name="gender"]').each(function(idx, elem) {
        $(elem).jqxRadioButton({
            groupName: 'gender',
            width: 25,
            height: 25,
            disabled: <c:choose><c:when test="${action == 'View'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>
        });
    });

    $('#employeeForm').on('click', '#btnCancel', function(e) {
        e.preventDefault();
        content.toggle();
    });

    <c:if test="${action != 'New'}">
        $('#employeeForm #birthDate').val('${employee.birthDate}');

        <c:choose>
            <c:when test="${employee.gender == 'F'}">
                $('#employeeForm div[data-value="F"]').jqxRadioButton('val', true);
                $('#employeeForm div[data-value="M"]').jqxRadioButton('val', false);
            </c:when>
            <c:otherwise>
                $('#employeeForm div[data-value="F"]').jqxRadioButton('val', false);
                $('#employeeForm div[data-value="M"]').jqxRadioButton('val', true);
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${action != 'View'}">
        $('#employeeForm #btnSave').jqxButton({template: 'primary'});

        $('#employeeForm').jqxValidator({
            hintType: 'label',
            position: 'bottomcenter',
            animationDuration: 0,
            rules: [
                {input: '#employeeForm #firstName', message: 'First Name is required', action: 'keyup', rule: 'required'},
                {input: '#employeeForm #birthDate', message: 'Birth Date is required', action: 'valueChanged', rule: function(input, commit) {
                    var date = $('#employeeForm #birthDate').jqxDateTimeInput('value');
                    if (date === null) return false;
                    return true;
                }},
                {input: '#employeeForm #birthDate', message: 'Must be at least 18 years old', action: 'valueChanged', rule: function(input, commit) {
                    var date = $('#employeeForm #birthDate').jqxDateTimeInput('value');
                    if (date === null) return false;
                    var today = new Date();
                    return (today.getFullYear() - date.getFullYear() >= 18);
                }},
                {input: '#employeeForm div[data-name="gender"]', message: 'Gender is required', action: 'change', rule: function() {
                    var checked = $('#employeeForm div[data-value="F"]').jqxRadioButton('checked') ||
                                  $('#employeeForm div[data-value="M"]').jqxRadioButton('checked') ;
                    return checked;
                }},
                {input: '#employeeForm #email', message: 'Email is required', action: 'keyup', rule: 'required'},
                {input: '#employeeForm #company', message: 'Company is required', action: 'select', rule: function(input) {
                    return ($.trim(input.val()).length > 0);
                }},
                {input: '#employeeForm #mobile', message: 'Mobile is required', action: 'keyup', rule: 'required'},
                {input: '#employeeForm #street', message: 'Street is required', action: 'keyup', rule: 'required'},
                {input: '#employeeForm #city', message: 'City is required', action: 'keyup', rule: 'required'},
                {input: '#employeeForm #zip', message: 'Zip is required', action: 'keyup', rule: 'required'}
            ]
        });

        $('#employeeForm').on('click', '#btnSave', function(e) {
            e.preventDefault();
            var valid = $('#employeeForm').jqxValidator('validate');
            console.log(valid);
            if (valid) {
                ajax.post({
                    url: '${context}/employee/save',
                    data: inputForm.serialize('employeeForm'),
                    callback: function(json) {
                        alert(json.messages.join('\n'));
                        if (json.statusCode === OK) {
                            content.restore('#searchForm', json.entity).trigger('#searchForm #btnSearch');
                            content.toggle();
                        }
                    }
                });
            }
        });
    </c:if>
});
</script>