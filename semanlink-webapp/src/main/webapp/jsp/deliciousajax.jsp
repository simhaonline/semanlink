<%@ page language="java" session="true" import="net.semanlink.delicious.*"%>
String what = request.getParameter("what");
// System.out.println("deliciuoasajax.jsp" + what);
if ("synchronext".equals(what)) {
	deliciousSynchro.synchroNext(request, response, out);
} else if ("importnext".equals(what)) {
	deliciousSynchro.importNext(request, response, out);
} else if ("exportnext".equals(what)) {
	deliciousSynchro.exportNext(request, response, out);
/*} else if ("importbundles".equals(what)) {
	deliciousSynchro.importBundles(request, response, out);	*/
} else {
	throw new RuntimeException("Unexpected parameter 'what' in request (or no such param)");
}
%>