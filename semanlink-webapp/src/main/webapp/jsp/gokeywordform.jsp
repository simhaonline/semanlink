<%@ page	language="java"	session="true"    pageEncoding="utf-8"    contentType="text/html;charset=UTF-8" %><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><!--gokeywordform.jsp--><%// ATTENTION : ci dessous, il faut du POST, sinon ca ne marche pas// (alors que ds la bande du web renault, il faut du get sur la m�me form !!! Why ? // TODO A VOIR%><html:form action="gokeyword" method="post">	<html:text property="kw" size="20"/>	<html:submit property="okBtn">Go</html:submit></html:form> <!--/gokeywordform.jsp-->