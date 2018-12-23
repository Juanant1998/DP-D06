<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<form:form modelAttribute="complaint" action="complaint/customer/edit.do">
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="report"/>
<form:hidden path="ticker"/>
<form:hidden path="moment"/>



<form:label path="description">
<spring:message code="attachment.description"/>
</form:label>
<form:textarea path="description"/>
<form:errors cssClass="error" path="description"/>

<form:label path="attachment">
<spring:message code="complaint.attachment"/>

</form:label>
<form:textarea path="attachment"placeholder ="url,url"/>
<form:errors cssClass="error" path="attachment"/>


<input type ="submit" name="save" value="<spring:message code=complaint.save/>" />

<input type="button" name="cancel" value="<spring:message code="complaint.cancel" />" onclick="javascript:relativeRedir('complaint/customer/list.do?customerId=${customerId}');" />
</form:form>

















