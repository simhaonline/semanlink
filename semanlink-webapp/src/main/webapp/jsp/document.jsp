<!--document.jsp-->

/*boolean issicgrdd = (
		(jsp instanceof net.semanlink.sicg.Jsp_SicgArticle)
		|| (jsp instanceof net.semanlink.sicg.Jsp_SicgRdd)
		|| (jsp instanceof net.semanlink.sicg.Jsp_RddIndex) );*/
boolean issicgrdd = false;

if (!edit) {%>
	<div class="doctitle">
		<%=jsp.getTitleInTitle()%>
	</div> <!-- class="title" -->
<%
	<div class="graybox">
		<div class="what"><%=jsp.i18l("doc.title")%></div>
		<html:form action="/setoraddproperty">
		<html:hidden property="uri" value="<%=uri%>" />
		<html:hidden property="docorkw" value="doc" />
		<html:hidden property="property" value="dc:title"/>
		<textarea name="docTitle" cols="80" rows="2"><%=jsp.getTitle()%></textarea>
		<br/>
		<html:select property="lang">
			<html:option value="-">-</html:option>
			<html:option value="fr">fr</html:option>
			<html:option value="en">en</html:option>
			<html:option value="es">es</html:option>
			<html:option value="pt">pt</html:option>
		</html:select>
		<html:submit property="<%=Action_SetOrAddProperty.SET%>">Set title</html:submit>
		</html:form>
	</div> <!-- class="graybox" -->






<%
}

String pagePathInfo = jsp.getPagePathInfo();
<jsp:include page="documenttags.jsp"/>
<% /////////////////// COMMENT		 %>							 
<jsp:include page="comment.jsp"/>

<%
boolean isNote = Note.isNote(uri);
if (!isNote) {
String href = jsp.getHREF();
%>
			if ( (uri.startsWith("file:")) || (jsp.isEditor()) ) {
			if (file.exists()) {
				
				if (false) {%><li><a href="<%=jsp.getLocalHREF()%>">File protocol URI</a></li><%}	
				
				
				
				
				
				long fileSize = file.length();
				}
				
				
				
				%>
			
			if (!(issicgrdd)) {
				if (jsp.isEditor()) {
					// liens vers doc, folder, finder
					%><li><a href="<%=jsp.getFolderPage(response)%>"><%=jsp.i18l("doc.parentFolderInSL")%></a></li><%
					%><li><a href="<%=jsp.getLocalFolderHREF()%>"><%=jsp.i18l("doc.parentFolderOnDisk")%></a> <%=jsp.i18l("doc.cpNavBar")%></li><%		
				} // jsp.isEditor() or not
			} // if (!(issicgrdd))
		
		
		} // file null or nor
		%>
<%} // (if isNote) %>