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

<display:table name="categories" id="row" requestURI="${requestURI}" pagesize="5" class ="displaytag">


<display:column property="name" titleKey="categories.name"/>
<display:column property="descendants" titleKey="categories.descendant"/>
<display:column>
<a href="categories/administrator/edit.do?categoryId=${row.id}">
<spring:message code="categories.edit"/>
</a>
</display:column>
</display:table>

<a href="categories/administrator/create.do">
<spring:message code="categories.create"/>
</a>








