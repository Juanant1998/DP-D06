
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

	
	<display:table pagesize = "5" class="displaytag" name="phases" requestURI="${requestURI}" id="row">
	
	<!-- ATTRIBUTES -->
	
	<spring:message code="phase.title" var= "titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false"  />
	 
	<spring:message code="phase.startMoment" var= "startMomentHeader" />
	<display:column property="startMoment" title = "${startMomentHeader}" sortable="false" format="{0,date,dd/MM/yyyy}"/>

	<spring:message code="phase.endMoment" var= "endMomentHeader" />
	<display:column property="endMoment" title = "${endMomentHeader}" sortable="false" format="{0,date,dd/MM/yyyy}"/>

	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<a href="phases/handyworker/edit.do?phaseId=${row.id}">
			<spring:message code="phase.editPhase"/>
			</a>
		</display:column>
	</security:authorize>
	
</display:table>

	<security:authorize access="hasRole('HANDYWORKER')">
			<a href="phases/handyworker/create.do?applicationId=${applicationId }">
			<spring:message code="phase.newPhase"/>
			</a>
	</security:authorize>
