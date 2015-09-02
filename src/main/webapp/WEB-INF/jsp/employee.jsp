<%@page autoFlush="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="resources" value="${pageContext.request.contextPath}/resources" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Employee</title>
        <jsp:include page="header.jsp" flush="true" />
    </head>
    <body>
        <div class="container">
            <div id="primary-content"></div>
            <div id="secondary-content" style="display:none;"></div>
        </div>
        <jsp:include page="footer.jsp" flush="true" />
        <jsp:include page="scripts.jsp" flush="true" />
        <script src="${resources}/common/js/theme.js"></script>
        <script>
            $(function() {
                ajax.get({
                    url: '${context}/employee/searchForm',
                    dataType: 'html',
                    callback: function(html) {
                        content.primary(html);
                    }
                });
            });
        </script>
    </body>
</html>