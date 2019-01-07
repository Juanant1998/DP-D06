
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<form:form action="section/handyworker/edit.do" modelAttribute="section">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<form:label path="title">
			<spring:message code="section.title" />:
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		
		<form:label path="text">
			<spring:message code="section.text" />:
		</form:label>
		<form:textarea path="text" />
		<form:errors cssClass="error" path="text" />
		<br />		
		
		<form:label path="pictures">
			<spring:message code="section.pictures" />:
		</form:label>
		<form:input path="pictures" />
		<form:errors cssClass="error" path="pictures" />
		<br />	
		
		<input type="submit" name = "save" value = "<spring:message code ="section.save" /> " />
		<spring:message code ="section.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('tutorial/handyworker/edit.do?tutorialId=${tutorialId}');" />
	
	
	
	</form:form>
	
	