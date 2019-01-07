<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<display:table pagesize = "5" class="displaytag" name="tutorials" requestURI="${requestURI}" id="row">

	<spring:message code="tutorial.title" var= "titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />

	
	<spring:message code="tutorial.handyworker" var= "handyworkerHeader" />
	<display:column property="handyworker" title="${handyworkerHeader}" sortable="false" />
	
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<a href="tutorial/handyworker.display.do?tutorialId=${row.id}">
			<spring:message code="tutorial.display"/>
			</a>
		</display:column>
		<display:column>
			<a href="section/handyWorker/edit.do?tutorialId=${row.id}">
				<spring:message	code="tutorial.editSection" />
			</a>
		</display:column>
	
	</security:authorize>
	</display:table>
	
	
	