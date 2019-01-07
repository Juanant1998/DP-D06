<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<b><spring:message code="tutorial.title"/></b>
<jstl:out value="${tutorial.title}"/>
<br/>

<b><spring:message code="tutorial.lastUpdated"/></b>
<jstl:out value="${tutorial.lastUpdated}"/>
<br/>

<b><spring:message code="tutorial.summary"/></b>
<jstl:out value="${tutorial.summary}"/>
<br/>

<b><spring:message code="tutorial.picture"/></b>
<jstl:out value="${tutorial.picture}"/>
<br/>

<b><spring:message code="tutorial.sections"/></b>
<br/>
<jstl:forEach var="section" items="${tutorial.sections}">
	<a href="${section}"><jstl:out value="${section}"/></a>
	<br/>
</jstl:forEach>
<br/>

<b><spring:message code="tutorial.handyworker"/></b>
<jstl:out value="${tutorial.handyworker}"/>
<br/>

<b><spring:message code="sponsorship.banner"/></b>
<jstl:out value="${banner}"/>
<br/>


<security:authorize access="hasRole('HANDYWORKER')">
	<spring:message code="tutorial.options"/>
	<a href="tutorial/handyworker.edit.do?tutorialId=${row.id}">
			<spring:message code="tutorial.edit"/>
	</a>
	<a href="tutorial/handyworker.delete.do?tutorialId=${row.id}">
			<spring:message code="tutorial.delete"/>
	</a>
</security:authorize>

