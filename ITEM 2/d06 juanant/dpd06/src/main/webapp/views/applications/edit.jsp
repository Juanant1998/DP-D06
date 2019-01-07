

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
	
	
	<form:form action="applications/handyworker/edit.do" modelAttribute="application">
			
			
			
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

	
<%-------------------------------------------------CUSTOMER EDIT--------------------------------------------------------------------%>
	
	
	<security:authorize access="hasRole('CUSTOMER')">
	
	<form:form action="applications/customer/edit.do" modelAttribute="application">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="status"/>

		<form:hidden path="offeredPrice"/>
		<form:hidden path="comments"/>
		<form:hidden path="creditCard.id"/>
		<form:hidden path="creditCard.version"/>
	
			
		<form:label path="creditCard.brandName">
		    <spring:message code="application.creditCard.brandName" />:
		</form:label>
		<form:input path="creditCard.brandName" />
		<form:errors cssClass="error" path="creditCard.brandName" />
		<br />		
		
		<form:label path="creditCard.holderName">
		    <spring:message code="application.creditCard.holderName" />:
		</form:label>
		<form:input path="creditCard.holderName" />
		<form:errors cssClass="error" path="creditCard.holderName" />
		<br />	
		
		<form:label path="creditCard.CVV">
		    <spring:message code="application.creditCard.CVV" />:
		</form:label>
		<form:input path="creditCard.CVV" />
		<form:errors cssClass="error" path="creditCard.CVV" />
		<br />	

		
		<form:label path="creditCard.number">
		    <spring:message code="application.creditCard.number" />:
		</form:label>
		<form:input path="creditCard.number" />
		<form:errors cssClass="error" path="creditCard.number" />
		<br />	
	
		<form:label path="creditCard.expirationMonth">
		    <spring:message code="application.creditCard.expirationMonth" />:
		</form:label>
		<form:input path="creditCard.expirationMonth" />
		<form:errors cssClass="error" path="creditCard.expirationMonth" />
		<br />	
	
			<form:label path="creditCard.expirationYear">
		    <spring:message code="application.creditCard.expirationYear" />:
		</form:label>
		<form:input path="creditCard.expirationYear" />
		<form:errors cssClass="error" path="creditCard.expirationYear" />
		<br />
		
		
		<input type="submit" name = "save" value = "<spring:message code ="application.save" /> " />
		<spring:message code ="application.cancel" var="cancel" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('applications/customer/list.do?customer=${customerId}');" />
		
	
	    </form:form> 
	
	 </security:authorize>   
	
	
	
	
	

