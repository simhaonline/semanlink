<!--kwblock_nb.jsp--><%/** * Suppose d�fini un bean Bean_KwList contenant la liste � afficher dans l'attribut de request "net.semanlink.servlet.Bean_KwList". * Bean_KwList doit avoir son attribut identifier d�fini (sinon prend la valeur "kwblock") */%><%@ page language="java" session="true" import="net.semanlink.servlet.*"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><%boolean edit = (Boolean.TRUE.equals(session.getAttribute("net.semanlink.servlet.edit")));Bean_KwList truc = (Bean_KwList) request.getAttribute("net.semanlink.servlet.Bean_KwList");if (truc != null) {	String identifier = truc.getIdentifier();	boolean listeSimple; // HACK A CHANGER pour savoir si on edit ou pas (cf linked keywords)	Jsp_Page jsp = (Jsp_Page) request.getAttribute("net.semanlink.servlet.jsp");	if (jsp instanceof Jsp_Document) {		listeSimple = true;		// truc.setJsp(jsp); // 2007/11 pour correction bug tag cloud sur dossier
	} else { // linked keywords		listeSimple = false;		truc.setJsp(jsp);	}	if (identifier == null) {		identifier = "kwblock";		listeSimple = false;	}						%>	<div class="browser">			<div class="title trigger">				<span><%=truc.getTitle()%></span>			</div>		<div id="block:<%=identifier%>" class="box"> <!-- on peut mettre scrollable ou box -->			<div class="kw2">				<% 
				// 2 SORTES DE LISTES : les listes simples qu'on peut �diter, et le tag cloud, qu'on ne peut pas �diter
				if ((edit) && (listeSimple)) { 
					// mais j'ai l'impression que ceci ne sert pas					request.setAttribute("putAddFormBefore",Boolean.TRUE);					%>					<jsp:include page="/jsp/kwlistedit.jsp" flush="true" />					<%					request.removeAttribute("putAddFormBefore");				} else {
					// tag cloud
					%>					<jsp:include page="/jsp/kwlist_nb2.jsp" flush="true" />				<%}%>			</div>		</div>	</div><!-- browser -->	<%} // if (truc != null)%><!--/kwblock_nb.jsp-->