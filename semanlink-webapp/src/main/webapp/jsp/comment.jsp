<!--comment.jsp-->
if (!edit) {
			<%
			if (comment.indexOf("<p>") > -1) { // BOF %>
				<%=comment%>
			<%} else { %>
				<p><%=comment%></p>
			<%}%>
		</div><%