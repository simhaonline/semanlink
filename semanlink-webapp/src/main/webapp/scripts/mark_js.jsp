<%@ page language="java" session="true" import="net.semanlink.servlet.*,net.semanlink.semanlink.*, java.util.*"
t='';
<%
String mainFrame = SLServlet.getMainFrame();
if (mainFrame != null) { // case of an app with frames
%>
	s=parent.<%=mainFrame%>.location;
} else {
	s=location.href;
}
<%
} else {
%>
s=location.href;
<%}%>
if (document.title) {
	t=document.title;
}

<%
// @find nir2tag
// look for a tag such as 
// <link rel="alternate" type="application/rdf+xml" href="http://dbpedia.org/data/William_of_Rubruck" title="RDF" />
// that would mean that this is an html page corresponding to a non information resource
%>
nir='';
el=document.getElementsByTagName('link');
if (el) {
	for(i=0;i < el.length;i++){
				if (rel.indexOf('alternate')!=-1 && type.indexOf('application/rdf+xml')!=-1) nir=el[i].getAttribute('href');
			}
	}
}

s = encodeURIComponent(encodeURIComponent(s));
t = encodeURIComponent(encodeURIComponent(t));
q = encodeURIComponent(encodeURIComponent(q));
l = encodeURIComponent(l);
nir = encodeURIComponent(encodeURIComponent(nir));

<%if (false) {%>
r='';
if (document.referrer) r=document.referrer;
if (typeof(_ref)!='undefined') r=_ref;
r = encodeURIComponent(r);
location.href='<%=net.semanlink.util.Util.getContextURL(request)%>/bookmarkform.do?docuri='+s+'&title='+t+'&comment='+q+'&lang='+l+'&via='+r;
<%}%>

location.href='<%=net.semanlink.util.Util.getContextURL(request)%>/bookmarkform.do?docuri='+s+'&title='+t+'&comment='+q+'&lang='+l+'&nir='+nir;