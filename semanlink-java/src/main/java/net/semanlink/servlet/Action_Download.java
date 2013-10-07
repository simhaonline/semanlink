package net.semanlink.servlet;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.*;

import org.apache.commons.httpclient.HttpException;
import org.apache.struts.action.*;

import net.semanlink.semanlink.*;
import net.semanlink.util.SimpleHttpClient;
import net.semanlink.util.Util;
import net.semanlink.util.html.HTMLPageDownload;

/*
 // YA 2 TRUCS NULS : 
  // 1) on lit le html une fois ici, et aune autre ds le addedDoc pour extraire metadata
   // 2) si problème lecture du html ici (générant une RuntimeException), on plante ds 
    // le HTMLPageDoowload, et on n'est même pas capable de suaver le fichier tel quel
     // (simple save) Ex :
      WARN [http-8080-Processor23] (RequestProcessor.java:538) - Unhandled Exception thrown: class net.semanlink.semanlink.SLRuntimeException
      java.lang.RuntimeException: Unexpected top level block close
      at javax.swing.text.html.CSSParser.getNextStatement(CSSParser.java:175)
      at javax.swing.text.html.CSSParser.parse(CSSParser.java:136)
      at javax.swing.text.html.StyleSheet$CssParser.parse(StyleSheet.java:3031)
      at javax.swing.text.html.StyleSheet.addRule(StyleSheet.java:272)
      at javax.swing.text.html.HTMLDocument$HTMLReader.addCSSRules(HTMLDocument.java:3345)
      at javax.swing.text.html.HTMLDocument$HTMLReader$HeadAction.end(HTMLDocument.java:2495)
      at javax.swing.text.html.HTMLDocument$HTMLReader.handleEndTag(HTMLDocument.java:2233)
      at net.semanlink.util.html.HTMLDocumentLoaderNew$1.handleEndTag(HTMLDocumentLoaderNew.java:143)
      at javax.swing.text.html.parser.DocumentParser.handleEndTag(DocumentParser.java:217)
      at javax.swing.text.html.parser.Parser.parse(Parser.java:2072)
      at javax.swing.text.html.parser.DocumentParser.parse(DocumentParser.java:106)
      at javax.swing.text.html.parser.ParserDelegator.parse(ParserDelegator.java:78)
      at net.semanlink.util.html.HTMLPageDownload.loadDocument(HTMLPageDownload.java:119)
      at net.semanlink.util.html.HTMLDocumentLoader.loadDocument(HTMLDocumentLoader.java:22)
      at net.semanlink.util.html.HTMLDocumentLoader.loadDocument(HTMLDocumentLoader.java:18)
      at net.semanlink.util.html.HTMLPageDownload.<init>(HTMLPageDownload.java:31)
      at net.semanlink.servlet.Action_Bookmark.execute(Action_Bookmark.java:52)
      */

/**
 * On arrive ici après Action_BookmarkForm (qui, autrefois, créait le HTMLDownload (et le mettait
 * ds la session via le Form_Bookmark)
 * 
 * Question des caratères spéciaux : résolu pour le short name, pas pour le path complet // TODO still true?
 */
public class Action_Download extends BaseAction {
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	try {
		SLModel mod = SLServlet.getSLModel();
		String uri = request.getParameter("docuri");
		// kwuri = java.net.URLDecoder.decode(uri);
		SLDocument doc = mod.getDocument(uri);
		//
		boolean overwrite = false;
		File file = downloadFile(uri, doc.getLabel(), overwrite, mod);
		setSource(uri, file, mod);
		//
		// POST REDIRECT
		// getJsp_Document(doc, request);
		// return mapping.findForward("continue");
		String redirectURL = Util.getContextURL(request) + HTML_Link.docLink(doc.getURI());
  	response.sendRedirect(response.encodeRedirectURL(redirectURL));
  	return null;
	} catch (Exception e) {
	    return error(mapping, request, e );
	}
} // end execute



static String getShortFilename(String title, String docuri, String contentType) {
	String dotExtension = null;
	if (docuri != null) {
		if (!docuri.endsWith(".")) {
			String sfn = Util.getLastItem(docuri, '/');
			dotExtension = Util.getDotExtension(sfn);
		}
	}
	
	/*
	if ((dotExtension == null) || ("".equals(dotExtension))) {
		if (contentType.indexOf("html") > -1) {
			dotExtension = ".html";
		} else {
			dotExtension = "";
		}
	}
	*/
	if ((contentType != null) && (contentType.indexOf("html") > -1)) {
		dotExtension = ".html";
	} else {
		if (dotExtension == null) dotExtension = "";
	}
	
	if (title != null) {
		String sfn = title.trim();
		if (sfn.length() > 32) sfn = sfn.substring(0,31);
		if (!("".equals(sfn))) return shortFilenameFromString(sfn) +  dotExtension;
	}
	String x = null;
	if ((docuri != null) && (!("".equals(docuri)))) {
		x = shortFilenameFromString(Util.getLastItem(docuri,'/'));
	}
	if (!("".equals(x))) return x;
	return "untitled.html"; // TODO
}


/** juste le remplacement des cars à la con. Pas d'extension la dedans */
public static String shortFilenameFromString(String sfn) {
	sfn = SLModel.converter.convert(sfn);
	// virer les car interdits. Ceci n'est pas suffisant? TODO
	sfn = sfn.replaceAll("/","-");
	sfn = sfn.replaceAll(":","-");
	sfn = sfn.replaceAll("'",""); // cf pb en javascript
	sfn = sfn.replaceAll("\"",""); // cf pb en javascript
	return sfn;
}

/**
 * Download et sauve un fichier.
 * Pour en plus dire que le fichier a pour source downloadFromUri, faire :
 * setSource(downloadFromUri, [the returned file], mod);
 * @param downloadFromUri
 * @param title
 * @param overwrite
 * @param mod
 * @return the new file.
 * @throws IOException 
 * @throws HttpException 
 * @throws RuntimeException if overwrite is false and file already exists
 */
public static File downloadFile(String downloadFromUri, String title, boolean overwrite, SLModel mod) throws HttpException, IOException {
	// if (!downloadFromUri.startsWith("http:")) throw new RuntimeException("Not an http uri"); // TODO : on pourrait vouloir faire une copie d'un file
	
	SimpleHttpClient simpleHttpClient = SLServlet.getSimpleHttpClient();
	String contentType = simpleHttpClient.getContentType(downloadFromUri, false);
	if (contentType == null) {
		if ((downloadFromUri.endsWith(".html"))||(downloadFromUri.endsWith(".htm"))) contentType = "text/html";
	}
	File dir = mod.goodDirToSaveAFile(); ////// !!!
	String sfn = getShortFilename(title, downloadFromUri, contentType);
	File saveAs = new File(dir, sfn);
	if (saveAs.exists()) {
		if (!overwrite) throw new RuntimeException ("A file " + saveAs.toString() + " already exists.");
	}

	// if ("text/html".equals(contentType)) {
	if ((contentType != null) && (contentType.indexOf("html") > -1)) {
		HTMLPageDownload download = new HTMLPageDownload(simpleHttpClient, new URL(downloadFromUri));
		if (HTMLPageDownload.isLeMondePrintPage(downloadFromUri)) {
			// remplacer le titre ds le html
			if (title != null) {
				download.replaceTitle(title);
			}
		}
		download.save(saveAs);						
	} else {
		simpleHttpClient.save(downloadFromUri, null, saveAs);
	}
	return saveAs;
}

/**
 * to state that file is a local copy of docUri
 * @param file
 * @param docUri
 * @param mod
 * @throws URISyntaxException 
 * @throws MalformedURLException 
 */
public static void setSource(String docUri, File file, SLModel mod) throws MalformedURLException, URISyntaxException {
	 String localUri = mod.fileToUri(file);
	 SLDocument localDoc = mod.getDocument(localUri);
	 mod.addDocProperty(localDoc, SLVocab.SOURCE_PROPERTY, docUri);

}
	
} // end Action
