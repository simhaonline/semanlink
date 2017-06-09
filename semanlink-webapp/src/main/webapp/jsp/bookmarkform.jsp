<!--bookmarkform.jsp-->
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
Boolean oneBtnOnlyB = (Boolean) request.getAttribute("oneBtnOnly");
//@find nir2tag
String nonInformationResourceUri = (String) request.getAttribute("nonInformationResourceUri");
%>
	<%=jsp.i18l("bookmarkform.1")%>
</div>
<div class="graybox">
	<script type="text/JavaScript">
		function godoc(s) {
			location.href=s;
		}	
		function showgodoc(s) {
			window.status=s;
		}	
		function valueByName(eltName) {
			return document.getElementsByName(eltName)[0].value;
		}
	</script>
	<%
	// I was using godoc(docuri.value)
	// works in safari (2.0.4), but doesn't in firefox (2.0.0.4)
	// (this link is necessary because we cannot go back to page where we clicked the bookmarklet: this is a pb with the bookmarklet.
	// Here, we go to the page given by the content of the input field - which is predocumented after the click on the bookmarklet)
	//
	// the onMouseOver doesn't work onMouseOver="showgodocByFldName('docuri')" (neither in safari, nor in firefox)
	// 
	%>
	<html:form action="bookmark" method="post">
		<b><a onclick="godoc(valueByName('docuri')); return false;" onMouseOver="showgodoc(valueByName('docuri'));return true" href="#" ><%=jsp.i18l("bookmarkform.docuri")%></a></b> <%=jsp.i18l("bookmarkform.docuri2")%><br/>
		<%
		if (false) { // tentative ds bookmarklet de trouver le lien vers une nir
			if ((SLServlet.isProto()) && (nonInformationResourceUri != null)) { %>
				<b><%=jsp.i18l("bookmarkform.nir")%></b><br/>
				<html:text property="nir" size="70"/><br/>
			<%}
		} // if (false) %>
		<b><%=jsp.i18l("x.title-colon")%></b><br/>
		<br/>
			<%
			if (false) { // tentative ds bookmarklet de trouver le lien vers une nir
				if (nonInformationResourceUri != null) { // @find nir2tag %>
					<br/>
					<html:submit property="nirTagBtn"><%=jsp.i18l("bookmarkform.createTag")%></html:submit>
				<%}
			} // if (false)
				
			// @find bookmark2tag
			%>
			<br/>
			<html:submit property="bookmark2tagBtn"><%=jsp.i18l("bookmarkform.createTag")%></html:submit>
			<%
		}
<%if (!oneBtnOnly) {%>
	<div class="graybox">
		<%=jsp.i18l("bookmarkform.2")%><br/>
	</div>
<%}%>


</div>

