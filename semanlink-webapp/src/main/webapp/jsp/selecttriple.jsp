<!--selecttriple.jsp--><%@ page language="java" session="true" import="net.semanlink.semanlink.*,net.semanlink.servlet.*,net.semanlink.util.*, java.util.*"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><style type="text/css"><!--.label {	width: 50px;	float: left;	margin-top:8px;}--></style><style type="text/css"><!--.choice {	text-align: right;}--></style><div class="title">	Select triple</div><div> <html:form action="selecttriple" method="get">	<!--	<span class="label">Subject:</span><html:text property="s" size="60"/><br>	<span style="width:300px;">Subject:</span><html:text property="s" size="60"/><br>	-->		<html:text property="s" size="60"/><span class="label">Subject</span><br>	<html:text property="p" size="60"/><span class="label">Property</span><br>	<html:text property="o" size="60"/><span class="label">Value</span><br><html:submit property="okBtn">OK</html:submit></html:form></div>	<p>ooops, ne marche pas si on documente juste la ppty, ou juste la value!!!	TODO</p><!--/selecttriple.jsp-->