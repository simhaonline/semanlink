<!--kwlist_nb2.jsp--><%/** * Suppose d�fini un bean Bean_KwList_Nb contenant la liste � afficher dans l'attribut de request "net.semanlink.servlet.Bean_KwList". */%><%@ page language="java" session="true" import="net.semanlink.servlet.*,net.semanlink.semanlink.*,java.util.*"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><%Bean_KwList_Nb truc = (Bean_KwList_Nb) request.getAttribute("net.semanlink.servlet.Bean_KwList");String contextPath = request.getContextPath();if (truc != null) {		int n = truc.size();		if (n > 0) {			%>				<%				for (int i = 0; i < n; i++) {					HTML_Link link = truc.getLink(i);
					/*int nb = truc.getNb(i);
					String nombre = null;					if (nb > 1) {						nombre = " (" + Integer.toString(nb) + ")";					} else {						nombre = "";					}*/
					String cla = "s" + truc.getNormalizedN(i);					/*if (nb < 6) {						cla = "s" + Integer.toString(nb);					} else {						cla = "s5";					}*/					// 2013-08 google nofollow rel="nofollow"					if (false){%><html:link page="<%=link.getPage()%>" styleClass="<%=cla%>"><%=link.getLabel()%></html:link> -<%}					%><a href="<%=response.encodeURL(contextPath + link.getPage())%>" rel="nofollow" class="<%=cla%>"><%=link.getLabel()%></a> - <%				} // for				%>			<%		} // if (n > 0)} // if (truc != null)%><!--/kwlist2.jsp-->