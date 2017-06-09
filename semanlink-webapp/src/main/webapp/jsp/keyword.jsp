<!--keyword.jsp-->
boolean new_showdocs = true;
boolean new_showTreeNotExpanded = true;
boolean new_showTreeExpanded = false;
boolean new_showTree = new_showTreeNotExpanded || new_showTreeExpanded;

%>
if (jsp.isDisplayParents()) {
				<jsp:include page="/jsp/kwlist.jsp" flush="true" />
<%}// if isDisplayParents %>

<%
if (!(jsp.isDisplaySnipOnly())) {
	/////////////////////////////////////////// TITLE
		String homePage = jsp.getHomePage();
		String getDescribedByPage = jsp.getFirstAsString(SLVocab.SL_DESCRIBED_BY_PROPERTY);
		boolean someStuffOnTheRight = ((homePage != null) || (getDescribedByPage != null));
		%>
			<%if (someStuffOnTheRight) { %>
				 <span style="float:right">
				 	<%if (homePage != null) { %>
				 		<a href="<%=homePage%>"><%=jsp.i18l("tag.homePage")%></a>
				 	<%} %>
				 	<%if (getDescribedByPage != null) { %>
				 		&nbsp;<a href="<%=getDescribedByPage%>"><%=jsp.i18l("tag.getDescribedByPage")%></a>
				 	<%} %>
				 </span>
			<%} // @find RDFa bergie // @find create.js bergie%>
	} else { // edit %>
						<html:option value="en">en</html:option>
						<html:option value="pt">pt</html:option>
	
	<% /////////////////// COMMENT		 %>							 
	<jsp:include page="comment.jsp"/>

<%}//  if (!(jsp.isDisplaySnipOnly())) %>
%>



<%if (!(jsp.isDisplaySnipOnly())) {%>
<div class="graybox"><%
	////// FRIENDS
	truc.setList(kw.getFriends());
	truc.setContainerAttr(null);
	truc.setUlCssClass(null);
	if (edit) {%>
		<% /////////////////////////////////////////// EDIT FRIENDS %>
		<%
			truc.setField("friends");
		%>
		<div class="what"><%=jsp.i18l("tag.friends")%></div>
		<div class="horizEnumeration">
			<jsp:include page="/jsp/kwlistedit.jsp" flush="true" />
		</div>
		<div class="clearboth"></div>
		<%
	} else {
		request.setAttribute("net.semanlink.servlet.rdfa.property",SLVocab.HAS_FRIEND_PROPERTY); // 2013-08 RDFa
		<div class="horizEnumeration">
			<div class="horizEnumerationTitle"><%=jsp.i18l("tag.friends")%></div>
			<jsp:include page="/jsp/kwlist.jsp" flush="true" />
		</div>
		<div class="clearboth"></div>
		<%
	}
	
%></div>
<%} %>



<% /////////////////////////////// CHILDREN ////////////////////////// %>




<div class="graybox">
<%
	boolean displayChildren = true; // to know whether they must be displayed later (set to false if tree displayed or...)	
			truc.setList(kw.getChildren());
			truc.setField("children");

	} else {
		// NOT EDIT

		DisplayMode displayMode = jsp.getDisplayMode();
				request.setAttribute("withdocs_notonfirstlevel", Boolean.TRUE);
				<%if (!(jsp.isDisplaySnipOnly()))  {
				%><div class="what"><%=jsp.i18l("tag.descendants")%><span style="float:right;text-size:small"><a href="<%=changeChildrenDisplay%>" rel="nofollow"><img src="<%=request.getContextPath()%>/ims/box_closed.gif" alt="<%=displayChildrenAs%>"/></a></span></div><%} %>
				<% /////////////////////////////////////////// IMAGE EVENTUELLE %>
				request.setAttribute("net.semanlink.servlet.rdfa.property","skos:narrower"); // 2013-08 RDFa
					<jsp:include page="/jsp/kwlist.jsp" flush="true" />
	} // edit or not





<% /////////////////////////////// DOCUMENTS ////////////////////////// %>





	<%
		java.text.MessageFormat messageFormat = new java.text.MessageFormat(jsp.i18l("x.documents"));
		Object[] args = new Object[1];
		args[0] = Integer.toString(nn);
		String ndocs = messageFormat.format(args);	
		String changeDocListDisplay = request.getContextPath()+jsp.computelinkToThis();
		String displayLongListOfDocs = null;



<%if (!(jsp.isDisplaySnipOnly())) { 
			if (edit) { %>												 
			<%} %>
			<jsp:include page="aliases.jsp"/>
			<jsp:include page="properties.jsp"/>
			<%if (SLServlet.isProto()) { %>
				<jsp:include page="tagOutsideLinks.jsp"/>
			<%}%>
<!--/keyword.jsp-->