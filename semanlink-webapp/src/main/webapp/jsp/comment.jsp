<!--comment.jsp--><%@ page language="java" session="true" import="net.semanlink.servlet.*,net.semanlink.semanlink.*,java.util.*"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><%Jsp_Resource jsp = (Jsp_Resource) request.getAttribute("net.semanlink.servlet.jsp");// PARAMETRAGE DU DISPLAYboolean edit = (Boolean.TRUE.equals(session.getAttribute("net.semanlink.servlet.edit")));//SLResource x = jsp.getSLResource();String uri =x.getURI();%><% /////////////////////////////////////////// COMMENTS %><%String comment = x.getComment();if (comment == null) comment = "";
if (!edit) {	if ((comment != null) && (!("".equals(comment)))) {		%><div class="graybox" property="rdfs:comment">
			<%
			if (comment.indexOf("<p>") > -1) { // BOF %>
				<%=comment%>
			<%} else { %>
				<p><%=comment%></p>
			<%}%>
		</div><%	} // comment !null} else { // edit	String docorkw;	if (x instanceof net.semanlink.semanlink.SLDocument) {		docorkw = "doc";	} else {		docorkw =  "kw";	}	%>	<div class="graybox">		<div class="what"><%=jsp.i18l("x.comment")%></div>		<p>		<html:form action="setcomment">			<html:hidden property="uri" value="<%=uri%>" />			<html:hidden property="docorkw" value="<%=docorkw%>" />			<!-- html:textarea property="comment" cols="80" rows="6" value="commentaire !!!!" / -->			<textarea name="comment" cols="80" rows="5"><%=comment%></textarea>			<br/>			<!-- html:hidden property="property" value="comment" / -->			<html:select property="lang">				<html:option value="-">-</html:option>				<html:option value="fr">fr</html:option>				<html:option value="en">en</html:option>				<html:option value="es">es</html:option>				<html:option value="pt">pt</html:option>			</html:select>			<html:submit property="<%=Action_SetOrAddProperty.SET%>"><%=jsp.i18l("x.setComment")%></html:submit>		</html:form>		</p>	</div>	<%}%><!--/comment.jsp-->