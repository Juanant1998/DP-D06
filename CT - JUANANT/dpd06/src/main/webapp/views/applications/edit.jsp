

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<%--<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 
--%>

	
	
		
		
<%-------------------------------------HANDY WORKER EDIT--------------------------------------------%>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	
	<form:form action="application/handyworker/edit.do" modelAttribute="application">
			
			
			
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
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('handyworker/application/list.do?hanyWorkerId=${handyWorkerId}');" />
	
	    </form:form> 	
	
	 </security:authorize>   

	
<%-------------------------------------------------CUSTOMER EDIT--------------------------------------------------------------------%>
	
	
	<security:authorize access="hasRole('CUSTOMER')">
	
	<form:form action="application/handyworker/edit.do" modelAttribute="application">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="offeredPrice"/>
		<form:hidden path="comments"/>
	
		
<form:label path="status">
		<spring:message code="application.status" />
	</form:label>

	<form:select path="status">
		<form:option label="----" value="0" />
		<form:options items="${status}" itemLabel="title" itemValue="id" />
	</form:select>

	<form:errors cssClass="error" path="status" />
	<br />
		
		<form:label path="creditCard">
		    <spring:message code="application.creditCard" />:
		</form:label>
		<form:input path="creditCard" />
		<form:errors cssClass="error" path="creditCard" />
		<br />		
		
	
		
		<input type="submit" name = "save" value = "<spring:message code ="application.save" /> " />
		<spring:message code ="application.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('applications/customer/list.do?customer=${customerId}');" />
		
	
	    </form:form> 
	
	 </security:authorize>   
	
	
	
	
	

