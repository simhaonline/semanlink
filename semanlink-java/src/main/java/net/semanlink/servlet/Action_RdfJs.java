package net.semanlink.servlet;
import java.net.URLDecoder;

import javax.servlet.http.*;
import org.apache.struts.action.*;


// @find rdfparser
/**
 */
public class Action_RdfJs extends BaseAction {
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	ActionForward x = null;
	try {
		x = mapping.findForward("continue"); // /jsp/rdfjs.jsp  -- this is a quick'n dirty test: devrait passer par le mécansime de template.jsp !! 
	} catch (Exception e) { // TODO : ceci n'est pas bon: devrait renvoyer un rdf d'erreur
	    return error(mapping, request, e );
	}
	return x;
} // end execute
} // end Action
