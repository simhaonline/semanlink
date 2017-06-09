package net.semanlink.servlet;
import glguerin.io.AccentComposer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import net.semanlink.semanlink.PropertyValues;
import net.semanlink.semanlink.SLDocument;
import net.semanlink.semanlink.SLKeyword;
import net.semanlink.semanlink.SLThesaurus;
import net.semanlink.util.FileUriFormat;

/**
 * De quoi documenter un lien HTML, et des methodes statiques pour les creer pour
 * les cas de SLDocument, SLKeyword,...
 * ATTENTION, on indique ici des actions qu'on demande de commencer avec un "/".
 * Tout ça à cause de ce con de struts
 * 
 * Avait été conçu 
 * - avant l'utilisation de liens "ala tags", pour des choses de la forme :
 * action.do?uri=urivalue
 * - spécialement pour l'utilisation par struts
 */
public class HTML_Link {
private String page, label;
HTML_Link(String page, String label) {
	this.page = page;
	this.label = label;
}
public String getPage() { return this.page; }
public String getLabel() { return this.label; }

//
// FACTORIES
//

/** @deprecated */
public static HTML_Link linkToDocument(SLDocument slDoc) throws UnsupportedEncodingException {
	// return linkToDocument(slDoc.getURI(), slDoc.getLabel(), "/showdocument.do");
	return new HTML_Link(htmlLinkPage(slDoc), slDoc.getLabel());
}

public static String getDocHref(HttpServletResponse response, String contextUrl, String docUri) throws UnsupportedEncodingException {
	// pb avec : 11 avril 1241 - les Mongols écrasent les Hongrois; l'Europe tremble.html ds CoolUriServlet
	// return response.encodeURL(contextUrl + "/doc/" + URLUTF8Encoder.encode(docUri));
	// avec slservlet :
	// return response.encodeURL(contextUrl + "/showdocument.do?docuri=" + URLEncoder.encode(docUri, "UTF-8"));
	// avec CoolUriServlet
	return response.encodeURL(contextUrl + docLink(docUri));
}

/** à utiliser en remplacement de linkToDocument (cf struts). */
public static String htmlLinkPage(SLDocument slDoc) throws UnsupportedEncodingException {
	// pb avec : 11 avril 1241 - les Mongols écrasent les Hongrois; l'Europe tremble.html ds CoolUriServlet
	// return "/doc/" + getRelativHREF(slDoc);
	// avec slservlet :
	// return "/showdocument.do?docuri=" + URLEncoder.encode(slDoc.getURI(), "UTF-8");
	// avec CoolUriServlet
	return docLink(slDoc.getURI());
}

public static String docLink(String docUri) throws UnsupportedEncodingException {
	return CoolUriServlet.DOC_SERVLET_PATH + "/?uri=" + URLEncoder.encode(docUri, "UTF-8");
}

// docUri supposed to be a local md file
public static String markdownLink(String mdDocUri) throws UnsupportedEncodingException {
	// return CoolUriServlet.MD_SERVLET_PATH + "/?uri=" + URLEncoder.encode(mdDocUri, "UTF-8");
	return docLink(mdDocUri);
}


/*marche pas : on perd les liens relatifs genre css, images, etc.
public static String fileUriServedByServletLink(String fileUri) throws UnsupportedEncodingException {
	return StaticFileServlet.PATH + "/?uri=" + URLEncoder.encode(fileUri, "UTF-8");
}*/


/**
 * @param action par ex "/showdocument.do"
 */
public static HTML_Link linkToDocument(SLDocument slDoc, String action) throws UnsupportedEncodingException {
	return linkToDocument(slDoc.getURI(), slDoc.getLabel(), action);
}

/**
 * @param action par ex "/showdocument.do"
 * @todo pourquoi pas URLUTF8Encoder.encode au lieu de URLEncoder.encode ??? // TODO
 */
public static HTML_Link linkToDocument(String docUri, String label, String action) throws UnsupportedEncodingException {
	// String page = "/showdocument.do?docuri=" + URLUTF8Encoder.encode(docUri);
	String page = action + "?docuri=" + URLEncoder.encode(docUri, "UTF-8");
	return new HTML_Link(page, label);
}

/** @deprecated */
public static HTML_Link linkToDocument(String docUri, String label) throws UnsupportedEncodingException {
	return linkToDocument(docUri, label, "/showdocument.do");
}

public static HTML_Link linkToFile(File dir, String shortFileName) throws UnsupportedEncodingException, URISyntaxException {
	// URI childUri = youri.resolve(shortFileName); // ne marche pas s'il y a des car speciaux			
	// en toute rigueur, il faudrait prendre ici la version definie dans le SLModel // Bug potentiel ? // TODO
	String furi = FileUriFormat.fileToUri(new File(dir, shortFileName));
	String label = AccentComposer.composeAccents(shortFileName);
	return linkToDocument(furi, label);
}

/** @deprecated dont use */
public static HTML_Link linkToKeyword(SLKeyword kw) throws UnsupportedEncodingException {
	try {
		throw new RuntimeException("This is way too much deprecated!"); // 2007/11 bug tagcloud sur dossier (et cr rdd)
	} catch (Exception e) {
		e.printStackTrace();
	}
	// return linkToKeyword(kw, "/showkeyword.do");
	return getHTML_Link(kw);
}

public static HTML_Link linkToKeyword(SLKeyword kw, String action) throws UnsupportedEncodingException {
	return linkToKeyword(kw.getURI(), kw.getLabel(), action);
}

public static HTML_Link linkToKeyword(String kwUri, String label, String action) throws UnsupportedEncodingException {
	String page = action + "?kwuri=" + URLEncoder.encode(kwUri, "UTF-8");
	return new HTML_Link(page, label);
}

public static HTML_Link linkToAndKws(SLKeyword firstKw, SLKeyword[] otherKws, String label) throws UnsupportedEncodingException {
	return linkToAndKws(firstKw, otherKws, label, "/andkws.do");
}

public static HTML_Link linkToAndKws(SLKeyword firstKw, SLKeyword[] otherKws, String label, String action) throws UnsupportedEncodingException {
	StringBuffer sb = new StringBuffer(128);
	sb.append(action);
	sb.append("?");
	SLKeyword[] kw0 = new SLKeyword[1] ; kw0[0] = firstKw;
	appendsKwsParams(kw0, sb);
	appendsKwsParams(otherKws, sb);
	String page = sb.toString();
	return new HTML_Link(page, label);
}

/** sb étant un début de lien, modifie sb en ajoutant les kws. 
 * @throws UnsupportedEncodingException*/
static void appendsKwsParams(SLKeyword[] kws, StringBuffer sb) throws UnsupportedEncodingException {
	for (int i = 0; i < kws.length; i++) {
		sb.append("&amp;kwuris=");
		sb.append(URLEncoder.encode(kws[i].getURI(), "UTF-8"));
	}
}
/**
 * @see Action_ShowProp
 */
public static HTML_Link linkToProp(String pptyuri, PropertyValues props, int index, String label) throws UnsupportedEncodingException {
	String objUri = props.getUri(index);
	if (objUri != null) {
		return linkToProp("/showprop.do", pptyuri, objUri, label);
	} else {
		return linkToProp("/showprop.do", pptyuri, props.getLiteral(index), props.getLang(index), label);
	}
}

public static HTML_Link linkToProp(String action, String pptyuri, String objuri, String label) throws UnsupportedEncodingException {
	return new HTML_Link(propAndKwsPage(action, pptyuri, objuri, label, (SLKeyword[]) null), label);
}
public static String propAndKwsPage(String action, String pptyuri, String objuri, String label, SLKeyword[] andKws) throws UnsupportedEncodingException {
	StringBuffer sb = new StringBuffer(128);
	sb.append(action);
	sb.append("?pptyuri=");
	sb.append(URLEncoder.encode(pptyuri, "UTF-8"));
	sb.append("&amp;objuri=");
	sb.append(URLEncoder.encode(objuri, "UTF-8"));
	if (andKws != null) {
		appendsKwsParams(andKws, sb);
	}
	return sb.toString();
}

public static HTML_Link linkToProp(String action, String pptyuri, String propValue, String lang, String label) throws UnsupportedEncodingException {
	return new HTML_Link(propAndKwsPage(action, pptyuri, propValue, lang, label, null), label);
}
/** lien vers "prop and (éventuellement) kws" */
public static String propAndKwsPage(String action, String pptyuri, String propValue, String lang, String label, SLKeyword[] andKws) throws UnsupportedEncodingException {
	StringBuffer sb = new StringBuffer(128);
	sb.append(action);
	sb.append("?pptyuri=");
	sb.append(URLEncoder.encode(pptyuri, "UTF-8"));
	sb.append("&amp;pptyval=");
	// Pb pour les car non ascii.
	// Si on se contente de 
	// sb.append(URLEncoder.encode(propValue, "UTF-8"));
	// alors on a ds le html qlq chose genre :
	// &pptyval=Fran%C3%A7ois-Paul+Servant
	// (ce qui donne, en copiant ds le presse papier, ou en regardant la dest en bas de page du brouteur :
	// ...&pptyval=François-Paul+Servant
	// Ca a l'air très bien, sauf que quand je fais getParameter("pptyval"), ceci donne :
	// FranÃ§ois-Paul Servant (même en tentant de faire request.setCharacterEnconding("UTF-8");
	// (idem avec safari, ns 7 sur mac et pc)
	// (Pourtant, request.getQueryString donne : 
	// pptyuri=http%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Fcreator&pptyval=Fran%C3%A7ois-Paul+Servant
	// (ca me semble donc être le getParameter qui ne se préoccupe pas de l'encoding)
	// ceci est ok : sb.append(URLEncoder.encode(URLUTF8Encoder.encode(propValue), "UTF-8"));
	sb.append(URLEncoder.encode(URLEncoder.encode(propValue, "UTF-8"), "UTF-8"));
	if ((lang != null) && !(lang.equals(""))) {
		sb.append("&amp;lang=");
		sb.append(lang);
	}
	if (andKws != null) {
		appendsKwsParams(andKws, sb);
		/*StringBuffer sb2 = new StringBuffer(label);
		for (int i = 0; i < andKws.length; i++) {
			sb2.append(" AND ");
			sb2.append(andKws[i].getLabel());
		}
		label = sb2.toString();*/
	}
	return sb.toString();
}

public static HTML_Link linkToThesaurus(SLThesaurus th) throws UnsupportedEncodingException {
	// String page = "/showthesaurus.do?uri=" + URLEncoder.encode(th.getURI(), "UTF-8");
	String page = "/sl/thesaurus?uri=" + URLEncoder.encode(th.getURI(), "UTF-8");
	return new HTML_Link(page, th.toString());
}

//
// LIES A LA NOUVELLE FACON DE LIER ALA "tag"
//

// private static String DEFAULT_THESAURUS_URI_DASH = SLServlet.getSLModel().getDefaultThesaurus().getURI()+"#"; // #thing
private static String DEFAULT_THESAURUS_URI_SLASH = SLServlet.getSLModel().getDefaultThesaurus().getURI()+"/"; // #thing
//private static int DEFAULT_THESAURUS_URI_DASH_LENGTH = DEFAULT_THESAURUS_URI_DASH.length();
private static int DEFAULT_THESAURUS_URI_SLASH_LENGTH = DEFAULT_THESAURUS_URI_SLASH.length();
/** Don't forget url rewriting when calling */
/*public static String getHREF(SLKeyword slkw) throws UnsupportedEncodingException {
	return getKwHREF(slkw.getURI());
}*/

// TODO changer : ne pas retourner /semanlink/
/*private static String getKwHREF(String kwuri) throws UnsupportedEncodingException {
	return "/semanlink/tag/" + getKwRelativHREF(kwuri);
}*/

/** Don't forget url rewriting when calling */
/*public static String getTagHREF(String kwUri, boolean resolveAlias) throws UnsupportedEncodingException {
	String x = getKwHREF(kwUri);
	if (resolveAlias) {
		if (x.indexOf("?") < 0) {
			x += "?resolvealias=true";			
		}
		x += "&amp;resolvealias=true";
	}
	return x;
}*/





/** FAUX :
 * L'uri complète est req.getServletPath() +"/" + HTML_Link.getRelativHREF(kw)
 * url relative à req.getServletPath() (par ex relative à xxx/tag/ ou xxx/rss/) 
 * (ce qu'il faut mettre au bout pour avoir l'uri du kw) 
 * 
 * Ceci retourne ce qu'il faut mettre après tag/
 */
public static String getRelativHREF(SLKeyword slkw) throws UnsupportedEncodingException {
	return getKwRelativHREF(slkw.getURI(), ".html");
}

/** 
 * ATTENTION COMMENT UN PEU FAUX : req.getServletPath() est par ex /doc
 * L'uri complète est req.getServletPath() +"/" + HTML_Link.getRelativHREF(sldoc)
 * url relative à req.getServletPath() (par ex relative à xxx/doc/) 
 * (ce qu'il faut mettre au bout pour avoir l'uri du doc) */
public static String getRelativHREF(SLDocument sldoc) throws UnsupportedEncodingException {
	// ne doit plus (?) être utilisé à cause du
	// pb avec : 11 avril 1241 - les Mongols écrasent les Hongrois; l'Europe tremble.html ds CoolUriServlet
	return getDocRelativHREF(sldoc.getURI());
}
public static String getDocRelativHREF(String docUri) throws UnsupportedEncodingException {
	return java.net.URLEncoder.encode(docUri,"UTF-8");
}

//
// 2006-02-14

/** l'extrème fin (après le "tag/"). Pas de résolution d'alias */ // 2007-01 (httprange-14)
public static String getKwRelativHREF(String kwUri, String dotExtension) throws UnsupportedEncodingException {
	String x;
	if (kwUri.startsWith(DEFAULT_THESAURUS_URI_SLASH)) {
		// inutile d'encoder, de par la façon dont est créé le tag (pas de car accentué)
		x = kwUri.substring(DEFAULT_THESAURUS_URI_SLASH_LENGTH);
		if (dotExtension != null) x += dotExtension;  // 2007-01 (httprange-14)
	} else {
		// ceci est OK avec Tomcat, mais quand on passe via apache,
		// ca ne va pas : la request n'est même pas passée à Tomcat
		// return java.net.URLEncoder.encode(kwUri,"UTF-8"); // encoding really needed ? yes I think
		x = kwUri;
		if (dotExtension != null) x += dotExtension;  // 2007-01 (httprange-14)
		x = "?uri=" + java.net.URLEncoder.encode(x,"UTF-8");  // 2007-01 (httprange-14)
	}
	return x;
}

/** l'extrème fin (après le "tag/").*/ // 2007-01 (httprange-14)
private static String getKwRelativHREF(String kwUri, String dotExtension, boolean resolveAlias) throws UnsupportedEncodingException {
	String x = null;
	if (kwUri.startsWith(DEFAULT_THESAURUS_URI_SLASH)) {
		// inutile d'encoder, de par la façon dont est créé le tag (pas de car accentué)
		x = kwUri.substring(DEFAULT_THESAURUS_URI_SLASH_LENGTH);
		if (dotExtension != null) x += dotExtension;  // 2007-01 (httprange-14)
		// if (resolveAlias) x += "?resolvealias=true";	// 2013-03 SKOSIFY
	} else {
		// ceci est OK avec Tomcat, mais quand on passe via apache,
		// ca ne va pas : la request n'est même pas passée à Tomcat
		// return java.net.URLEncoder.encode(kwUri,"UTF-8"); // encoding really needed ? yes I think
		x = kwUri;
		if (dotExtension != null) x += dotExtension;  // 2007-01 (httprange-14)
		x = "?uri=" + java.net.URLEncoder.encode(x,"UTF-8");  // 2007-01 (httprange-14)
		// if (resolveAlias) x += "&amp;resolvealias=true"; // 2013-03 SKOSIFY
	}
	return x;
}

//NOT USED ???
/** 
 * L'url d'un tag, longue (http://...), avec URL rewriting
 * @param contextUrl @see Util.getContextURL(HttpServletRequest) ou Jsp_Page.getContextURL()
 * pas de résolution d'alias
 */
public static String getTagHref(HttpServletResponse response, String contextUrl, String kwUri) throws UnsupportedEncodingException {
	// return response.encodeURL(contextUrl + "/showdocument.do?docuri=" + URLUTF8Encoder.encode(docUri));
	return response.encodeURL(getTagURL(contextUrl, kwUri, false, ".html"));
}

/** 
 * L'url d'un tag, sans URL rewriting ,
 * longue (http://...), si on passe
 * dans contextUrl @see Util.getContextURL(HttpServletRequest) ou Jsp_Page.getContextURL()
 * OU BIEN non comprise le host:port (commençant par /)
 * si on passe dans contextUrl request.getContextPath (ex "/semanlink"),
 * @deprecated : use form getTagURL with dotExtension explicitely
 */
public static String getTagHref(String contextUrl, String kwUri, boolean resolveAlias) throws UnsupportedEncodingException {
	return getTagURL(contextUrl, kwUri, resolveAlias, ".html");
}

/**
 * L'url d'un tag, sans URL rewriting ,
 * longue (http://...), si on passe
 * dans contextUrl @see Util.getContextURL(HttpServletRequest) ou Jsp_Page.getContextURL()
 * OU BIEN non comprise le host:port (commençant par /)
 * si on passe dans contextUrl request.getContextPath (ex "/semanlink"),
 * @param contextUrl
 * @param kwUri
 * @param resolveAlias
 * @param dotExtension ".html" ou ".rdf"
 * @return
 * @throws UnsupportedEncodingException
 */
public static String getTagURL(String contextUrl, String kwUri, boolean resolveAlias, String dotExtension) throws UnsupportedEncodingException {
	return contextUrl + CoolUriServlet.TAG_SERVLET_PATH + "/" + getKwRelativHREF(kwUri, dotExtension, resolveAlias);
}




/** to ne used with struts html:link tag */ // ? // 2007/11 bug tagcloud sur dossier (et cr rdd)
public static HTML_Link getHTML_Link(SLKeyword kw) throws UnsupportedEncodingException {
	return new HTML_Link(CoolUriServlet.TAG_SERVLET_PATH + "/" + getKwRelativHREF(kw.getURI(), ".html"), kw.getLabel()) ;
}

}