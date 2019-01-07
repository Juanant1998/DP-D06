
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



	
	<form:form action="actor/edit.do" modelAttribute="actor">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="messageBoxes" />
		<form:hidden path="userAccount.authorities" />
	
		<security:authorize access="hasRole()">
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />
		
		<form:label path="middleName">
			<spring:message code="actor.middleName" />
		</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br />
		
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br />
		
		<form:label path="photo">
			<spring:message code="actor.photo" />
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br />
		
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br />
		
		<form:label path="phone">
			<spring:message code="actor.phone" />
		</form:label>
		<form:input path="phone" />
		<form:errors cssClass="error" path="phone" />
		<br />
		
		<form:label path="address">
			<spring:message code="actor.address" />
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br />
		
		<form:label path="profiles">
			<spring:message code="actor.profiles" />
		</form:label>
		<form:textarea path="profiles" placeholder="Enter social profiles separated with ','"/>
		<form:errors cssClass="error" path="profiles" />
		<br />
		
		</security:authorize>
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	
	
	
	<input type="button" name="cancel" value="<spring:message code="actor.cancel" />" />

<!-- Lo de abajo no está bien, hay que hacerlo viendo que el id != 0 -->
	
</form:form>
