<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="complaints" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">


<display:column property="moment" titleKey="complaint.moment" sortable="true" format="{0,date,dd/MM/yyyyy HH:mm}"/>
<display:column property="ticker" titleKey="complaint.ticker" sortable="true"/>
<security:authorize access="hasRole('CUSTOMER')">
<display:column>
<a href="complaint/customer/edit.do?complaintId=${row.id}">
<spring:message code="complaint.edit"/>

</a>
</display:column>
<display:column>
<a href="complaint/customer/display.do?complaintId=${row.id}"></a>
<spring:message code="complaint.display"/>
</display:column>
<display:column>
			<a href="complaint/customer/delete.do?complaintId=${row.id}">
				<spring:message	code="complaint.delete" />
			</a>
		</display:column>
</security:authorize>
</display:table>









