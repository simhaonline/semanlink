<!--rdfTypesForm.jsp-->
<%
// FORM TO EDIT rdf:type of tags
%>
<script type="text/JavaScript"><!--
function selectedRdfTypeChanged(what) {
    var selectedOptionValue = what.options[what.selectedIndex].value;
    var selectedOptionText = what.options[what.selectedIndex].text;
    document.getElementById('rdfType').value = selectedOptionText;
}

function rdfTypeChanged() {
	alert("rdfTypeChanged");
    document.getElementById('selectRdfType').options[0].selected = true;
}

//--></script>

<div class="graybox">
<div class="what">rdf:type</div>

<%
	<html:hidden property="docorkw" value="<%=docorkw%>" />
	<html:hidden property="property" value="rdf:type" />
	<b>rdf:type </b><html:text styleId="rdfType" property="value" size="40" onchange="rdfTypeChanged()"/>
		<html:option value="-">-</html:option>
		<%
		Iterator it = jsp.rdfTypes4Tags();
		for(;it.hasNext();) {
			String typeValue = it.next().toString();
			%>
	<br/>
</div>
<!--/rdfTypesForm.jsp-->