<!--thesaurus.jsp-->
if (false) {
} // if (false)
%>
<%
java.text.MessageFormat messageFormat = new java.text.MessageFormat(jsp.i18l("x.tags"));
Object[] args = new Object[1];
args[0] = Integer.toString(jsp.getSize());
String ntags = messageFormat.format(args);		
