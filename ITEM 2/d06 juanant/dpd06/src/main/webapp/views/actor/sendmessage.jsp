
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



	
	<form:form action="message/actor/send.do" modelAttribute="message">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="sender" />
	
		
		<form:label path="subject">
			<spring:message code="message.subject" />
		</form:label>
		<form:input path="subject" />
		<form:errors cssClass="error" path="subject" />
		<br />
		
		<form:label path="recipients">
			<spring:message code="message.recipients" />
		</form:label>
		<form:textarea path="recipients" />
		<form:errors cssClass="error" path="recipients" />
		<br />
	
		
		<form:label path="body">
			<spring:message code="message.body" />
		</form:label>
		<form:textarea path="body" />
		<form:errors cssClass="error" path="body" />
		<br />
		
		<form:label path="tags">
			<spring:message code="message.tags" />
		</form:label>
		<form:textarea path="tags" />
		<form:errors cssClass="error" path="tags" />
		<br />
		
		<form:label path="priority">
			<spring:message code="message.priority" />
		</form:label>
		<form:input path="priority" placeholder="HIGH, MEDIUM OR LOW"/>
		<form:errors cssClass="error" path="priority" />
		<br />
		
	
	<input type="submit" name="save" value="<spring:message code="message.save" />" />
	
	
	
	<input type="button" name="cancel" value="<spring:message code="message.cancel" />" />

</form:form>
