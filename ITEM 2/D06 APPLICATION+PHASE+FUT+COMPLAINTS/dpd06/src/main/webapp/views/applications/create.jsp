

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

		
<%-------------------------------------HANDY WORKER EDIT--------------------------------------------%>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	
	<form:form action="applications/handyworker/savefut.do" modelAttribute="application">
			
			
			
	    <form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
	    <form:hidden path="creditCard"/>
		<form:hidden path="status"/>
	
		
		<form:label path="offeredPrice">
			<spring:message code="application.offeredPrice" />:
		</form:label>
		<form:input path="offeredPrice" />
		<form:errors cssClass="error" path="offeredPrice" />
		<br />
		
		<form:label path="comments">
			<spring:message code="application.comments" />:
		</form:label>
		<form:input path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br />		
		
	
		<input type="submit" name = "save" value = "<spring:message code ="application.save" /> " />
		<spring:message code ="application.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('aplications/handyworker/list.do');" />
	
	    </form:form> 	
	
	 </security:authorize>   

	
	
	
	
	

