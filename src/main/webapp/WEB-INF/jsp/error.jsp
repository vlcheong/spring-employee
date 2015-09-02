<%@page autoFlush="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="$error" class="hide">
    <div id="data">
        <ul>
        <c:forEach var="error" items="${errors}">
            <li>${error}</li>
        </c:forEach>
        </ul>
    </div>
</div>