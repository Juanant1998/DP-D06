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





<form:form modelAttribute="category" action="categories/administrator/edit.do">
<form:hidden path="id"/>
<form:hidden path="version"/>




<form:label path="name">
<spring:message code="categories.edit.name"/>
<form:input path="name" />
		<br />
</form:label>

<form:select path="descendants">
		    <form:option label="----" value="0" />
		    <form:options items="${list}" itemLabel="id" itemValue="id" />
		    </form:select>
<br />
<input type ="submit" name="save" value="<spring:message code="categories.edit.save"/>" />

<input type="button" name="cancel" value="<spring:message code="complaint.cancel" />" onclick="javascript:relativeRedir('categories/administrator/list.do');" />


<input type ="submit" name="delete" value="<spring:message code="categories.delete"/>" />

</form:form>










