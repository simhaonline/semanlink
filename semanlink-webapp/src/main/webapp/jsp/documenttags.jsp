<!--documenttags.jsp--><%/** */%><%@ page language="java" session="true" import="net.semanlink.servlet.*"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><%Jsp_Resource jsp = (Jsp_Resource) request.getAttribute("net.semanlink.servlet.jsp");boolean edit = (Boolean.TRUE.equals(session.getAttribute("net.semanlink.servlet.edit")));jsp.prepareParentsList();%><div class="graybox">		<%if (edit) {%>			<div class="horizEnumeration">
			<div class="what">Tags: </div>			<jsp:include page="/jsp/kwlistedit.jsp" flush="true" />			</div>		<%} else {%>			<span class="horizEnumerationTitle">Tags: </span>			<div class="horizEnumeration">
			<jsp:include page="/jsp/kwlist.jsp" flush="true" />			<div class="clearboth"></div>			</div>		<%} // if edit or not%></div><!--/documenttags.jsp-->