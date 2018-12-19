

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

	
	<form:form action="finder/handyworker/edit.do" modelAttribute="finder">
		
		
	<%-----------------------------------FORM FINDER----------------------------------%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="lastModified"/>
	
		
		<form:label path="query">
			<spring:message code="finder.query" />:
		</form:label>
		<form:input path="query" />
		<form:errors cssClass="error" path="query" />
		<br />
		
		<form:label path="minPrice">
			<spring:message code="finder.minPrice" />:
		</form:label>
		<form:input path="minPrice" />
		<form:errors cssClass="error" path="minPrice" />
		<br />
		
		<form:label path="maxPrice">
			<spring:message code="finder.maxPrice" />:
		</form:label>
		<form:input path="maxPrice" />
		<form:errors cssClass="error" path="maxPrice" />
		<br />
		
		<form:label path="startDate">
			<spring:message code="finder.startDate" />:
		</form:label>
		<form:input path="startDate" />
		<form:errors cssClass="error" path="startDate" />
		<br />
		
		<form:label path="endDate">
			<spring:message code="finder.endDate" />:
		</form:label>
		<form:input path="endDate" />
		<form:errors cssClass="error" path="endDate" />
		<br />
		
		<form:label path="warranties">
		    <spring:message code="finder.warranties" />
	    </form:label>

	    <form:select path="warranties">
		    <form:option label="----" value="0" />
		    <form:options items="${warranties}" itemLabel="title" itemValue="id" />
	    </form:select>

	    <form:errors cssClass="error" path="warranties" />
	    <br />
		
		
	<%-----------------------------------FIX-UP TASKS----------------------------------------%>
		
	<spring:message code="fixuptask.moment" var= "momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	 
	<spring:message code="fixuptask.description" var= "descriptionHeader" />
	<display:column property="description" title = "${descriptionHeader}" sortable=false/>

	<spring:message code="fixuptask.address" var= "addressHeader" />
	<display:column property="address" title = "${addressHeader}" sortable=false/>

	<spring:message code="fixuptask.maximumPrice" var= "maximumPriceHeader" />
	<display:column property="maximumPrice" title = "${maximumPriceHeader}" sortable=false/>
	
	<spring:message code="fixuptask.startDate" var= "startDateHeader" />
	<display:column property="startDate" title = "${startDateHeader}" sortable=false/>
	
	<spring:message code="fixuptask.endDate" var= "endDateHeader" />
	<display:column property="endDate" title = "${endDateHeader}" sortable=false/>
	
	<spring:message code="fixuptask.warranty" var= "warrantyHeader" />
	<display:column property="warranty" title = "${warrantyHeader}" sortable=false/>	
	
	<spring:message code="fixuptask.category" var= "categoryHeader" />
	<display:column property="category" title = "${categoryHeader}" sortable=false/>		
		
		
		
		
	<%--------------------------------------BUTTONS----------------------------------------%>	
	
		
	<input type="submit" name="search" value="<spring:message code="finder.search"/>" />
		
		
	
	
	</form:form>
	
	

