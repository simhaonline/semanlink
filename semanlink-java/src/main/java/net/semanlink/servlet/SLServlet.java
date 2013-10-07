package net.semanlink.servlet;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.semanlink.metadataextraction.MetadataExtractorManager;
import net.semanlink.semanlink.*;
import net.semanlink.servlet.SemanlinkConfig.LoadException;
import net.semanlink.sljena.*;
import net.semanlink.util.CopyFiles;
import net.semanlink.util.SimpleHttpClient;
import net.semanlink.util.servlet.BasicServlet;

public class SLServlet extends org.apache.struts.action.ActionServlet {
/** The debugging detail level for this servlet. */
private static int debug = 0;
/** Une hashtable de JModel. Clé : url du model relatif à la servlet. (cf contextPath) */
static private HashMap slModelH = new HashMap();
static private ArrayList slModelList = new ArrayList(); // a virer
static private WebServer webServer;
static private SemanlinkConfig.ApplicationParams applicationParams;
/** mais en fait pour le moment on n'en gère qu'un seul. */
// static private SLModel slModel;
/** params pour les choses genre sortProperty, getTemplate, etc */
public static Params_Jsp jspParams;
public static boolean DEBUG = false;
private static String servletURL;
/** url of semanlink web site. */
public static String SEMANLINK_DOT_NET = "http://www.semanlink.net";
private static File mainDataDir;
public static File getMainDataDir() { return mainDataDir; }
public static File getConfigDir() { return new File(mainDataDir,"conf"); }
private static String semanlinkVersion;
private static MetadataExtractorManager metadataExtractorManager;
private static SimpleHttpClient simpleHttpClient;
// pour la liste des props utilisées ds les forms
private static SemanlinkConfigProps semanlinkConfigProps;
// private static SLSPARQLEndPoint sparqlEndPoint;

/**
  * Gracefully shut down this servlet, releasing any resources that were allocated at initialization.
  */
public void destroy() {
    if (debug >= 1) log("Finalizing SLServlet");
    getServletContext().removeAttribute("net.semanlink.servlet.semanlinkservlet");
}

/** Un vieux commentaire dit :
 * "j'ai tenté ça pour pb encodage de char non ascii ds des params de href, sans succès"
 * Mais je doute -- voir le doPost.
 */
public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	req.setCharacterEncoding("UTF-8");
	// res.setContentType("text/html; charset=UTF-8  "); // ceci ne semble pas nécessaire // commented 2007-12 VOIRE NUISIBLE: si c'est autre chose qu'on veut retourner
	// MAIS attention a bien avoir un entête de jsp du genre
	/*<%@ page
  contentType="text/html;charset=UTF-8" 
  pageEncoding="UTF-8"
	language="java"
	session="true"
	import="net.semanlink.servlet.*,java.io.*"
	 */
	handleParams(req);
	
	/*
	if ("/sparql".equals(req.getPathInfo())) {
			// go to sparql page
			Jsp_Page jsp = sparqlPage(req, res);
			req.setAttribute("net.semanlink.servlet.jsp", jsp);
			RequestDispatcher requestDispatcher;
			try {
				requestDispatcher = req.getRequestDispatcher(jsp.getTemplate());
			} catch (Exception e) { throw new RuntimeException(e); }
		  requestDispatcher.forward(req, res);
		  return;
	} else if ("/sparql/".equals(req.getPathInfo())) {
		JModel slModel = (JModel) getSLModel();
		if (sparqlEndPoint == null) sparqlEndPoint = new SLSPARQLEndPoint(slModel.getKWsModel(), slModel.getDocsModel());
		sparqlEndPoint.exec(req, res);
		return;
	}
	*/

	super.doGet(req, res);
}

protected Jsp_Page sparqlPage(HttpServletRequest req, HttpServletResponse res) {
	Jsp_Page x = new Jsp_Page(req);
	x.setTitle("SPARQL");
	x.setContent("/jsp/sparql.jsp");
	return x;
}


/** Le req.setCharacterEncoding("UTF-8") est absolument indispensable pour éviter les problèmes
 *  avec les caractères non ASCII dans les forms. Sans ça, on récupère par ex
 *  √© quand on a saisi un é.
 *  Et ATTENTION, il faut vraiment que ça soit au tout début (j'avais une fois mis
 *  une série de System.out.println(req.getTruc()) et ça n'allait plus.
 */
public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	req.setCharacterEncoding("UTF-8");
	// res.setContentType("text/html; charset=UTF-8  "); // ceci ne semble pas nécessaire // commented 2007-12 VOIRE NUISIBLE: si c'est autre chose qu'on veut retourner
	/*if (SLServlet.DEBUG) {
		printRequestInfo(req);
	}*/
	handlePostParams(req);
	handleParams(req);
	// System.out.println("SLServlet doPost session valid : " + req.isRequestedSessionIdValid() + " id : " + req.getSession().getId());
	// System.out.println("mode : " + req.getSession().getAttribute("net.semanlink.servlet.mode"));
	super.doPost(req, res);
}

static void handlePostParams(HttpServletRequest request) {
	String action = request.getParameter("do");
	HttpSession session = request.getSession();
	if ("setprefs".equals(action)) {	
		BaseAction.param2SessionAttribute("mode", request);
		
	    String sortProp = BaseAction.getPropertyUri(request);
	    if ( (sortProp  != null) && (!("".equals(sortProp))) ) {
	    		session.setAttribute( "net.semanlink.servlet.SortProperty", sortProp);
	    } else {
	    		session.setAttribute( "net.semanlink.servlet.SortProperty", SLVocab.HAS_KEYWORD_PROPERTY); // cas * Keywords *
	    }
	    
	    booleanParamIntoAttribute("imagesonly", request, session);		
	}
}

static void handleParams(HttpServletRequest req) {
	/*System.out.println("Parameters:");
	Enumeration e = req.getParameterNames();
	for (;e.hasMoreElements();) {
		String s = (String) e.nextElement() ;
		System.out.println("\t"+s+" : " + req.getParameter(s));
	}*/
	// HttpSession session = req.getSession();
	// BasicServlet.printRequestInfo(req);

	handleBooleanParam("editor",req);
	handleBooleanParam("edit",req);
	handleBooleanParam("gomenu",req);
	//2013-03 pour pourvoir switcher sur la page la liste des docs long / court		
	if (req.getParameter("longListOfDocs") != null) {
		HttpSession session = req.getSession();
		if (session != null) {
			handleBooleanParam("longListOfDocs",req); 
			Boolean lld = (Boolean) session.getAttribute("net.semanlink.servlet.longListOfDocs");
			if (lld == null) lld = Boolean.FALSE;
		  DisplayMode displayMode = new DisplayMode((String) session.getAttribute("net.semanlink.servlet.childrenAs")
		  		, lld.booleanValue());
		  session.setAttribute("net.semanlink.servlet.displayMode", displayMode);
		}
	}
	
	// 2013-04 expand children tree on page
	String s = req.getParameter("childrenAs");
	if (s != null) {
		HttpSession session = req.getSession();
		if (session != null) {
			req.getSession().setAttribute("net.semanlink.servlet.childrenAs", s);
			Boolean boo = (Boolean) session.getAttribute("net.semanlink.servlet.longListOfDocs");
			if (boo == null) boo = DisplayMode.DEFAULT.isLongListOfDocs();
		  DisplayMode displayMode = new DisplayMode(s, boo.booleanValue());
		  session.setAttribute("net.semanlink.servlet.displayMode", displayMode);
		}
	}
	

	String paramName = "lang";
	s = req.getParameter(paramName);
	if (s != null) {
		req.getSession().setAttribute("net.semanlink.servlet." + paramName, s);
	}
}

static void booleanParamIntoAttribute(String paramName, HttpServletRequest request, HttpSession session) {
    String s = request.getParameter(paramName);
    Boolean b = Boolean.FALSE;
    if (s != null) {
    		b = Boolean.valueOf(s);
    }
    session.setAttribute("net.semanlink.servlet."+paramName, b);
}


private static void handleBooleanParam(String paramName, HttpServletRequest req) {
	String s = req.getParameter(paramName);
	if (s != null) {
		s = s.toLowerCase();
		Boolean b = null;
		if ("true".equals(s)) {
			b = Boolean.TRUE;
		} else if ("false".equals(s)) {
			b = Boolean.FALSE;			
		} else if ("flip".equals(s)) { // à vérifier
			b = (Boolean) req.getSession().getAttribute("net.semanlink.servlet."+paramName);
			if (b == null) {
				b = Boolean.TRUE;
			} else {
				b = new Boolean(!b.booleanValue());
			}
		}
		if (b != null) req.getSession().setAttribute("net.semanlink.servlet."+paramName,b);	
		if (b != null) req.getSession().setAttribute(paramName,b);	
	}
}

public void init() throws ServletException {
    super.init();

     
    
    // initialization parameters
    String value;
    value = getServletConfig().getInitParameter("debug");
    try { debug = Integer.parseInt(value); } catch (Throwable t) { debug = 0; }
    DEBUG = (debug >= 1);
    if (debug >= 1) log("Initializing semanlink servlet...");

    setJspParams(newJspParams()); // must be done before initSL

    initSL();
 
    // JModel jmod = (JModel) this.slModel;
    // JenaUtils.print(jmod.getDocsModel());
    /*
    value = getServletConfig().getInitParameter("pathname");
    if (value != null) pathname = value;
    */

   // @todo : ou bien mettre le SLModel ?
    getServletContext().setAttribute("net.semanlink.servlet.semanlinkservlet", this);
    
    // 2013-09 maven, change location of file in order to use "resource filtering" to put values from maven into it
 		// InputStream in = getServletContext().getResourceAsStream("/WEB-INF/properties");
 		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/properties");
		if (in != null) {
		  Properties properties = new Properties();
		  	try {
					properties.load(in);
					this.semanlinkVersion = (String) properties.get("version") +"-"+ (String) properties.get("version-date");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
    
	/** String favoriUri = slModel.kwLabel2ExistingKwUri("favori", Locale.FRANCE); // bof
	if (favoriUri != null) favori = slModel.getKeyword(favoriUri); **/

	
	/*debugWeakHashMap();
	System.out.println("APRES GC");
	System.gc();
	System.gc();
	System.gc();
	debugWeakHashMap();*/
		
		System.out.println("SLServlet.init end getDefaultDateProperty(): " + getDefaultDateProperty() + " getDefaultSortProperty() : " + getDefaultSortProperty());
} // init


/** création des params utilisés pour les choses genre sortProperty, getTemplate, etc... */
public Params_Jsp newJspParams() { return new Params_Jsp(); }
public static Params_Jsp getJspParams() { return jspParams; }
public static void setJspParams(Params_Jsp p) { jspParams = p; }

/** Return the debugging detail level for this servlet. */
public int getDebug() { return (this.debug);  }

static public SLModel getSLModel() {
  /** return slModel; **/
	// ATTENTION : ceci suppose que slModelList a déjà été documenté !
	return (SLModel) slModelList.get(0); // TODO
}


//--------------------------------------------------------- 
// ON DEVRAIT ds slmodel faire 2 méthodes de chargement : une pour les kwsfile, une pour les docsfile // TODO
void initSL() throws ServletException {
	trace("SLServlet.initSL");
	try {
		ServletConfig config = getServletConfig();
		ServletContext context = config.getServletContext();
	
		servletURL = context.getInitParameter("semanlinkURL");
		System.out.println("servletURL " + servletURL);
		// if ((servletURL == null) || ("".equals(servletURL))) throw new RuntimeException("Context parameter 'semanlinkURL' not defined.");
		if ((servletURL == null) || ("".equals(servletURL))) servletURL = "http://www.semanlink.net"; // KATTARE TO DO 
		ModelFileIOManager.init(servletURL);

		String s;
		s = config.getInitParameter("welcomePageJsp");
		if (s == null) throw new RuntimeException("InitParameter welcomePageJsp not defined");
		jspParams.setWelcomePageJsp(s);

		s =  config.getInitParameter("semanlinkDotNet");
		if (s !=null) SEMANLINK_DOT_NET = s;
		
		s =  context.getInitParameter("semanlinkDataDir");
		System.out.println("semanlinkDataDir " + s);
		if (s == null) throw new RuntimeException("Context parameter 'semanlinkDataDir'. It must define an existing dir where servlet can store data.");
		mainDataDir = getFile(s, context, false);
		if (mainDataDir == null) throw new RuntimeException("File corresponding to context parameter 'semanlinkDataDir' \"" + s + "\" doesn't exist."); // OK


		
		// This must be AFTER the definition of mainDataDir (because use getConfigDir)
		String lang = context.getInitParameter("lang");
		Locale locale = null;
		if (lang != null) {
			boolean ok = false;
			locale = new Locale(lang);
			Locale[] locs = Locale.getAvailableLocales();
			for (int i = 0; i < locs.length; i++) {
				if (locs[i].getLanguage().equals(lang)) {
					ok = true;
				}
			}
			if (!ok) {
				locale = null;
			}
		}
		if (locale == null) locale = Locale.getDefault();
		I18l.initAvailableLanguages(this, locale);
		
			

		String semanlinkConfigShortFilename = config.getInitParameter("semanlinkConfigShortFilename");
		File configDir = getConfigDir();
		if (!configDir.exists()) {
			boolean ok = configDir.mkdirs();
			if(!ok) throw new RuntimeException("Impossible to create conf directory: " + configDir);
		}
		File configFile = new File(configDir, semanlinkConfigShortFilename);
		if (!configFile.exists()) {
			// config file doesn't exist yet: install default version into mainDataDir
			log("Installing default configuration file into " + configDir);
			/* File source = new File(context.getRealPath("/WEB-INF"), semanlinkConfigShortFilename);
			if (!source.exists()) throw new RuntimeException("Default configuration file doesn't exist. Please report a bug: there should be a file called " + semanlinkConfigShortFilename + " in the WEB-INF dir.");
			// no problem to copy from inside a war file ???
			// CopyFiles.copyFile(source, configFile); */
			InputStream in = context.getResourceAsStream("/WEB-INF/" + semanlinkConfigShortFilename);
			if (in == null) throw new RuntimeException("Configuration file template doesn't exist. Please report a bug: there should be a file called " + semanlinkConfigShortFilename + " in the WEB-INF dir.");
			OutputStream out = new FileOutputStream(configFile);
			CopyFiles.writeIn2Out(in, out, new byte[1024]);
			out.close();
		} else {
			// config file already exists: no problem
		}

  	SemanlinkConfig semanlinkConfig = new SemanlinkConfig(configFile, servletURL, context);
  	
  	this.slModelList = new ArrayList();
  	semanlinkConfig.load(this.slModelList);
  	this.webServer = semanlinkConfig.getWebServer();
  	this.applicationParams = semanlinkConfig.getApplicationParams();
  	for (int i = 0; i < slModelList.size() ; i++) {
  		SLModel mod = (SLModel) slModelList.get(i);
  		// attention, ce truc ne recharge pas le model :
    	// si on l'utilise, il faut donc quitter juste après et relancer
  		// (la différence avec les corrections habituelles, c'est qu'on a besoin d'avoir
  		// déjà chargé le slModel pour définir la correction (ensemble de corrections pour être plus précis)
  		// System.out.println("SLServlet:correctOldKwUris");
    	// ((JModel) mod).correctOldKwUris(); // commenter (mais A GARDER!!!)
  		// ((JModel) mod).correctAliasFromAltLabel(); // A VIRER -- APRES CA FAIRE QUITER PUIS RELANCER
  		
  		// mod.listenDocs();
  		slModelH.put(mod.getModelUrl(), mod);
  		mod.setMetadataExtractorManager(getMetadataExtractorManager());
  		//
  		mod.endInit();
  	}
  	
  	// les propriétés à utiliser
  	semanlinkConfigProps = new SemanlinkConfigProps(new File(configDir, "props.rdf"), servletURL);
  	
  	///
  	
  } catch (Exception ex) {
	  	System.err.println("Exception dans initSL :");
	  	ex.printStackTrace();
	  	throw new ServletException(ex);
  }
  
}

/** 
 * si n'existe pas : retourne null si !throwException, sinon lève une exception. 
 * prend en compte la possibilité d'avoir des fichiers relatifs à la servlet.
 * (si fileName n'existe pas, tente le realPath) */
private static File getFile(String filename, ServletContext servletContext, boolean throwException) {
		// System.out.println("getFile " + filename);
		File f = null;
		try {
			f = new File(filename);
		} catch (Exception e) { // A VIRER (cf explosion kattare)
			System.err.println("EXCEPTION " + filename);
			e.printStackTrace();
		}
		if ((f == null) || (!f.exists())) {
			String svg = filename;
			filename = servletContext.getRealPath(filename);
			if (filename != null) {
				f = new File(filename);
				if (!f.exists()) {
					String s = "File \"" + svg +"\" doesn't exist, realPath: " + filename;
					if (throwException) {
						throw new LoadException(s);
					} else {
						return null;
					}
				}
			} else {
				String s = "File \"" + svg +"\" doesn't exist, realPath: " + null;
				if (throwException) throw new LoadException(s);
			}
		}
		return f;
}



/** Création demandée d'un nouveau Model.
  *  Pour le moment, on n'en gère qu'un seul à la fois. */
/**void newSLModel() {
  this.slModel = new JModel();
}**/

/*SLModel newSLModel() {
	SLModel x = new JModel();
	this.slModelList.add(x);
	return x;
}*/

//
//
//

//


public static WebServer getWebServer() { return webServer; }


// PAS BON TODO
public static SLModel getSLModel(HttpServletRequest request) {
	/*String contextPath = request.getContextPath();
	SLModel x = (SLModel) slModelH.get(contextPath);
	if (x == null) { // BOF BOF TODO
		if ("".equals(contextPath)) {
			contextPath = "/";
		} else {
			contextPath = "";
		}
		x = (SLModel) slModelH.get(contextPath);
	}*/
	String reqUrl = request.getRequestURL().toString();
	for (int i = 0; i < slModelList.size(); i++) {
		SLModel x = (SLModel) slModelList.get(i);
		if (reqUrl.startsWith(x.getModelUrl())) {
			return x;
		}
	}
	return (SLModel) slModelList.get(0);
}

static public String getServletUrl() { return servletURL; }

public static String getDefaultSortProperty() { return applicationParams.getDefaultSortProperty(); }
public static String getDefaultDateProperty() { return applicationParams.getDefaultDateProperty(); }
public static boolean isEditorByDefault() { return applicationParams.isEditorByDefault(); }
public static boolean isProto() { return applicationParams.isProto(); }
/** @return Returns the logonPage, soit absolue, soit relative au host (pas à la servlet semanlink!) (arrive ici via applicationParams forcément absolue) */
public static String getLogonPage() { return applicationParams.getLogonPage(); }
// public static boolean isTrace() { return applicationParams.isTrace(); }
public static void trace(String s) {
	// if (applicationParams.isTrace()) System.out.println(s);
	if ((applicationParams != null) && (applicationParams.isTrace())) {
		System.out.println(s);
	} else {
		if (DEBUG) System.out.println(s);
	}
}
public static String getMainFrame() { return applicationParams.getMainFrame(); }
public static String getSemanlinkVersion() { return semanlinkVersion; }
//
//
//

public static MetadataExtractorManager getMetadataExtractorManager() {
	if (metadataExtractorManager == null) {
		// metadataExtractorManager = new MetadataExtractorManager(getSimpleHttpClient());
		metadataExtractorManager = new MetadataExtractorManager();
	}
	return metadataExtractorManager;
}
public static SimpleHttpClient getSimpleHttpClient() {
	if (simpleHttpClient == null) {
		if (applicationParams.getProxyHost() != null) {
			simpleHttpClient = new SimpleHttpClient(
					applicationParams.getProxyHost(), applicationParams.getProxyPort(), applicationParams.getProxyUserName(), applicationParams.getProxyPassword()
			);
		} else {
			simpleHttpClient = new SimpleHttpClient();
		}
	}
	return simpleHttpClient;
}
public static String getProxyHost() {
	return applicationParams.getProxyHost();
}
public static int getProxyPort() {
	return  applicationParams.getProxyPort();
}
public static String getProxyUserName() {
	return applicationParams.getProxyUserName();
}
public static String getProxyPassword() {
	return applicationParams.getProxyPassword();
}
public static boolean isSemanlinkWebSite() {
	return applicationParams.isSemanlinkWebSite();
}

//
//
//

// public static String prefix2NameSpace(String nsPrefix) { return semanlinkConfigProps.prefix2NameSpace(nsPrefix); }
public static SemanlinkConfigProps getSemanlinkConfigProps() { return semanlinkConfigProps; }
public static SLVocab.EasyProperty[] getEasyProps() {
	return semanlinkConfigProps.getEasyProps();
}

//
//
//

}
