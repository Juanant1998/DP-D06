
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<style>
tr.rejected{
background-color: red
}
tr.accepted{
background-color: green
}
</style>

	<%--CREAR LA TABLA E INTRODUCIOR CADA DATO DE APPICATIONS DENTRO DE UNA COLUMNA --%>
	
<display:table pagesize = "5" class="displaytag" name="applications" requestURI="${requestURI}" id="row">
	
	<!-- ATTRIBUTES -->
	
	<spring:message code="application.moment" var= "momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="application.offeredPrice" var= "offeredPriceHeader" />
	<display:column property="offeredPrice" title = "${offeredPriceHeader}" sortable="false"/>
	
	<spring:message code="application.comments" var= "commentsHeader" />
	<display:column property="comments" title = "${commentsHeader}" sortable="false"/>	
	
	<spring:message code="application.status" var= "statusHeader" />
	<display:column property="status" title = "${statusHeader}" sortable="false"/>	
	


    
    <security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<a href="applications/handyworker/edit.do?applicationId=${row.id}">
			<spring:message code="application.edit"/>
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
		
	
		<display:column>
		<jstl:if test = "${row.status =='PENDING' }">
			<a href="applications/customer/reject.do?applicationId=${row.id}">
			<spring:message code="application.reject"/>
			</a>
			 </jstl:if>
		</display:column>
		<display:column>
		<jstl:if test = "${row.status =='PENDING' }"> 
			<a href="applications/customer/accept.do?applicationId=${row.id}">
			<spring:message code="application.accept"/>
			</a>
			 </jstl:if>
		</display:column>

		
	</security:authorize>
	
</display:table>


