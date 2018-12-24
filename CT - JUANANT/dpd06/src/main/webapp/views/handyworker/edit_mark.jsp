<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form action="handyworker/edit_mark.do" modelAttribute="handyworker">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="profiles" />
		<form:hidden path="messageBoxes" />
		<form:hidden path="userAccount" />
		<form:hidden path="name" />
		<form:hidden path="middleName" />
		<form:hidden path="surname" />
		<form:hidden path="photo" />
		<form:hidden path="phone" />
		<form:hidden path="address" />
		<form:hidden path="email" />
		
		
		<form:label path="mark">
			<spring:message code="actor.mark" />
		</form:label>
		<form:input path="mark" />
		<form:errors cssClass="error" path="mark" />
		<br />
		
			<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	
	
	<input type="button" name="cancel" value="<spring:message code="actor.cancel" />" />		
		
		
		</form:form>