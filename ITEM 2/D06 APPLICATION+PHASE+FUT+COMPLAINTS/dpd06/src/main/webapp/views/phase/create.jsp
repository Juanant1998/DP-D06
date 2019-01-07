
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



	
	<form:form action="phases/handyworker/create.do" modelAttribute="phase">
		<form:hidden path="id"/>
		<form:hidden path="version"/>

		
		<form:label path="title">
			<spring:message code="phase.title" />:
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		
		<form:label path="description">
			<spring:message code="phase.description" />:
		</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br />		
		
		<form:label path="startMoment">
			<spring:message code="phase.startMoment" />:
		</form:label>
		<form:input path="startMoment" placeholder="01/01/2000"/>
		<form:errors cssClass="error" path="startMoment" />
		<br />	
		
		<form:label path="endMoment">
			<spring:message code="phase.endMoment" />:
		</form:label>
		<form:input path="endMoment" placeholder="01/01/2000" />
		<form:errors cssClass="error" path="endMoment" />
		<br />	
		
		<input type="submit" name = "save" value = "<spring:message code ="phase.save" /> " />
		<spring:message code ="phase.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('/index.do');" />

	
	</form:form>
	