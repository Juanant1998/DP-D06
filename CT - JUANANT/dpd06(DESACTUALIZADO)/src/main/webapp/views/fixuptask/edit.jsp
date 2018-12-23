
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



	
	<form:form action="fixuptask/customer/edit.do" modelAttribute="fixuptask">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="ticker"/>
		
		<form:label path="description">
			<spring:message code="fixuptask.description" />:
		</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br />
		
		<form:label path="address">
			<spring:message code="fixuptask.address" />:
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br />		
		
		<form:label path="maximumPrice">
			<spring:message code="fixuptask.maximumPrice" />:
		</form:label>
		<form:input path="maximumPrice" />
		<form:errors cssClass="error" path="maximumPrice" />
		<br />	
		
		<form:label path="startDate">
			<spring:message code="fixuptask.startDate" />:
		</form:label>
		<form:input path="startDate" />
		<form:errors cssClass="error" path="startDate" />
		<br />	
		
		<form:label path="endDate">
			<spring:message code="fixuptask.endDate" />:
		</form:label>
		<form:input path="endDate" />
		<form:errors cssClass="error" path="endDate" />
		<br />	
		
		<form:label path="warranty">
			<spring:message code="fixuptask.warranty" />:
		</form:label>
		<form:input path="warranty" />
		<form:errors cssClass="error" path="warranty" />
		<br />	
	
		<form:label path="category">
			<spring:message code="fixuptask.category" />:
		</form:label>
		<form:input path="category" />
		<form:errors cssClass="error" path="category" />
		<br />	
		
		
		<input type="submit" name = "save" value = "<spring:message code ="fixuptask.save" /> " />
		<spring:message code ="fixuptask.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('fixuptask/customer/list.do?customerId=${customerId}');" />

	
	</form:form>
	