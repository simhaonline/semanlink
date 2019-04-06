package net.semanlink.servlet;
import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import net.semanlink.semanlink.SLDocument;
import net.semanlink.semanlink.SLKeyword;
import net.semanlink.semanlink.SLModel;
import net.semanlink.semanlink.SLSchema;
import net.semanlink.semanlink.SLUtils;
import net.semanlink.semanlink.SLVocab;
import net.semanlink.util.Util;
import net.semanlink.util.html.HTMLPageDownload;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
 * On arrive ici après Action_BookmarkForm
 * 
 * Question des caratères spéciaux : résolu pour le short name, pas pour le path complet // TODO still true?
 */
public class Action_Bookmark extends BaseAction {
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	// ActionForward x = null;





	String redirectURL = null;
	SLModel mod = SLServlet.getSLModel();
	// passons en mode "edit"
	setEdit(request, true);

	Form_Bookmark bookmarkForm = (Form_Bookmark) form;
	String title = bookmarkForm.getTitle().trim();
	if ("".equals(title)) title = null;
	String lang = bookmarkForm.getLang();
	if (lang != null) lang = lang.trim();
	if ("".equals(lang)) lang = null;
	String comment = bookmarkForm.getComment().trim();
	if ("".equals(comment)) comment = null;

	try {




		SLDocument docToDisplay = null; // doc to be displayed
		String docuri = request.getParameter("docuri"); // pas de decode : issu d'un champ de saisie, non code (?)
		if ((docuri == null) || ("".equals(docuri))) {
			return error(mapping, request, "No document's URI");
		}

		// docuri peut venir 
		// 1) de la bookmarklet - auquel cas, elle a été URLDecoder.decodée, et se présente par ex
		// sous la forme http://a/b/un exemple éé.html
		// 2) d'un paste de uri récoltée sur une de nos pages (ou, au moins avant, d'une valeur prédoc
		// à l'uri d'une de nos pages) - auquel cas elle est déjà toute bien comme il faut, avec
		// des %20 et des %C3
		// 3) d'une saisie -- auquel cas elle peut être n'importe quoi,
		// y compris complétement fause
		// Pour remettre d'équerre ce qui viendrait du cas 1, et refuser les erreurs du cas 3,
		// on fait la chose suivante (cf Test.testUriSmall)
		docuri = SLUtils.laxistUri2Uri(docuri);

		SLDocument docOnline = mod.smarterGetDocument(docuri);

		if (request.getParameter("bookmark2tagBtn") != null) { 		// @find bookmark2tag
			
			//
			// CREATION OF A TAG FROM THE URI OF A BOOKMARK
			//
			
			String kwLabel = title;
			if (kwLabel == null) return error(mapping, request, "No label for tag");
			Locale locale = null;
			if (lang != null) {
				locale = new Locale(lang);
			} else {
				// 2017-08
				// locale = Locale.getDefault();
				locale = null;
			}
			SLKeyword kw = mod.kwLabel2KwCreatingItIfNecessary(kwLabel, mod.getDefaultThesaurus().getURI(), locale);
			String kwUri = kw.getURI();
			mod.addKwProperty(kwUri, SLVocab.SL_DESCRIBED_BY_PROPERTY, docOnline.getURI());
			if (comment != null) mod.addKwProperty(kwUri, SLVocab.COMMENT_PROPERTY, comment, lang); // si on était sûr que c'est un new kw, on pourrait faire set seulement

			redirectURL = HTML_Link.getTagURL(Util.getContextURL(request), kwUri, false, ".html");





		} else {
			
			//
			// CREATION OF A BOOKMARK (OR LOCAL DOC) (if doesn't exist yet)
			//
			
			// 2019-03 URIS for bookmarks
			
			
			
			// Before uris for bookmarks:
			//
			// il y a plusieurs cas possibles de pré-existence de l'url du docOnLine dans le mdoèle, mais
			// on ne s'occupe pas de tous ici, ce qui se justifie
			// - par le fait qu'il sont traités dans Action_BookmarkForm 
			// (qui envoie directement sur la page, par ex du tag ql le doc est homopage du tag)
			// - et que ça permet de tout de même créer un doc (via "new bookmark" dans la barre de droite)
			// pour un tel cas si on y tient vraiment
			//
			// D'OU LE SIMPLE TEST existsAsSubject avant 2019-03
			
			// 2019-03 : on se préoccupe uniquement de l'existence du doc en tant que bookmark
			SLDocument bookmark2019 = mod.bookmarkUrl2Doc(docOnline.getURI());
			if (bookmark2019 != null) {
				
				docToDisplay = bookmark2019;
				
			} else if (mod.existsAsSubject(docOnline)) { // true ssi doc intervient dans au moins un statement en tant que sujet

				// ALREADY EXISTS (en tant que doc)
				
				// - ne prend pas en compte le cas où c juste source d'un doc local, ou un truc pointé par un tag
				// MAIS CELA EST TRAITE AVANT, DS Action_BookmarkForm
				
				docToDisplay = docOnline;
				
			} else {
				
				String downloadFromUri = request.getParameter("downloadfromuri"); // pas de decode : issu d'un champ de saisie, non code (?)
				if ((downloadFromUri == null) || ("".equals(downloadFromUri))) {
					downloadFromUri = docuri;
				} else {
					downloadFromUri = SLUtils.laxistUri2Uri(downloadFromUri);	    	
				}

				boolean downloadRequested = ((request.getParameter("bookmarkWithCopyBtn") != null)
						|| (request.getParameter("copyWithBookmarkBtn") != null));
				Response res = null;
				boolean isHTML = false;
				String dotExtension = null;
				if (downloadRequested) {
					res = Action_Download.getResponse(downloadFromUri);		
					isHTML = Action_Download.isHTML(downloadFromUri, res);
					dotExtension = ".html";
				} else {
					dotExtension = Util.getDotExtension(downloadFromUri);
				}

						
				SLDocument doc = null; // local ou online, c'est selon.
				// Attention ceci n'est pas bon, car reste documenté d'un appel à l'autre
				// (ou alors il faudrait le mettre ces infos à null en fin de execute)
				// if (bookmarkForm.getBookmarkBtn() != null) {
				SLDocument localDoc_SourceToBeAdded = null; // sera !null ds les cas "bookmark avec copy" ou "local avec source" (et égal a localDoc)
				// si on souhaite stocker la source
				// (il faut prendre garde de ne pas créer en 1er le statement SOURCE_PROPERTY,
				// (qui a nécessité un truc spécial dans le "onNewDocument" du listener jena
				// pour ne pas entrainer la création des statements de new doc pour la source)
				// parce que sinon, il n'y a pas le traitement new doc 
				
				// 2019-03 uris for bookmarks
				// which (sl) uri for this new bookmark?
				// elt's create it from title (as we were doing for files)
				if (title == null) return error(mapping, request, "No title for doc");
				String ln = Action_Download.title2uriPathComponent(title);
				
				// il faut vérifier :
				// que l'uri correspondante n'existe pas déjà
				// qu'un fichier n'existe pas déjà non plus (pour être sûr d'avoir
				// des noms d'uri de bookmark et de downloaded file qui correspondent)
				// Si existe, déjà suffixer par _i
				
				// quelle serait l'uri complète de bookmark ?
				
				String bkmUri = null;
				
				// pour la calcul du ns l'uri du bkm, je ne sais pas bien comment faire
				// à partir du SLDataFolder, à part faire comme s'il s'agissait d'un fichier,
				// comme fait dans Action_NewNote; // TODO améliorer ça (?)
				
				// the dir to save the data about bookmark's uri
				File bkmDir = mod.dirToSaveBookmarks();
				// the dir to save the file if we save a copy
				File saveAsDir = mod.goodDirToSaveAFile();
				String sfn = ln; // sans dot extension, par ex "titre_du_bkm"
				SLDocument bkm = null;
				File saveAs = null; // the file for the saveAs
				int i = 0;
				for(;;) {
					bkmUri = mod.fileToUri(new File(bkmDir, sfn));
					bkm = mod.getDocument(bkmUri);
					if (!mod.existsAsSubject(bkm)) {
						saveAs = new File(saveAsDir, sfn + dotExtension);
						if (!saveAs.exists()) {
							break;
						}
					}
					i++;
					sfn = ln + "_" + i;														
				}

				if (downloadRequested) {
					Action_Download.download(downloadFromUri, saveAs, false, res, isHTML);	
				}
				
				if (request.getParameter("bookmarkBtn") != null) {
					
					// clic on "Bookmark" btn
					
					// doc = docOnline;
					doc = bkm;
					
					mod.setDocProperty(bkm, SLVocab.SL_BOOKMARK_OF_PROPERTY, docOnline.getURI());
					mod.setDocProperty(bkm, SLVocab.TITLE_PROPERTY, title, lang);
					if (comment != null) mod.setDocProperty(bkm, SLVocab.COMMENT_PROPERTY, comment, lang);
	
					
				} else {
					
					// boolean overwrite = param2boolean("overwrite", request, false); // 2019-03 : this param never set to true, semble-t-il
					// File saveAs = Action_Download.downloadFile(downloadFromUri, title, overwrite, mod); // title sert à créer le nom du fichier

					String localUri = mod.fileToUri(saveAs);
					SLDocument localDoc = mod.getDocument(localUri);
					
					if (request.getParameter("bookmarkWithCopyBtn") != null) {
						// doc = docOnline;
						doc = bkm;
						
						localDoc_SourceToBeAdded = localDoc;

						mod.setDocProperty(bkm, SLVocab.SL_BOOKMARK_OF_PROPERTY, docOnline.getURI());
						mod.setDocProperty(bkm, SLVocab.TITLE_PROPERTY, title, lang);
						if (comment != null) mod.setDocProperty(bkm, SLVocab.COMMENT_PROPERTY, comment, lang);
						// source : l'affecter à la vraie source (doconline) ou bien au bkm doc ???
						if (localDoc_SourceToBeAdded != null) {
							// AH,MAIS ATTENTION !!!
							// Supposons qu'on change le lien bookmarkOf
							// La copie locale a un dc:source qui n'est plus lié au bkm
							// et ne peut donc plus être retrouvée à partir de lui
							
							// essayons en mettant les 2
							// ne doit pas gêner pour affichage du lien source sur docline bkm

							// 1) à la source : la vraie source online ? -> localFile dc:source onlineUrl // avantage : lien 1 pour 1 au cas où on aurait plusiuers saved docs attachés au bkm
							mod.addDocProperty(localDoc_SourceToBeAdded, SLVocab.SOURCE_PROPERTY, docOnline.getURI());
							// 2) ou bien le bkm ? -> localFile dc:source bkm // avantage : doit marcher sans modif du code pre2019
							mod.addDocProperty(localDoc_SourceToBeAdded, SLVocab.SOURCE_PROPERTY, bkm.getURI());		
														
						}

						
						
						
					} else if (request.getParameter("copyWithBookmarkBtn") != null) {
						localDoc_SourceToBeAdded = localDoc;
						// ne pas faire ça maintenant, sinon on perd les ajouts de metadata
						// on new doc, becoz of un traitement spécial pour la prop source (voir listeenr
						// avec enw doc)
						// mod.addDocProperty(addSourceTo, SLVocab.SOURCE_PROPERTY, docuri);

						// CREER OU PAS UN "BKM" ???
						// oui -> on aurait aussi pour les docs locaux la possibilité de changer l'uri du doc pointé
						// 			  mais : à la fois uri /doc/... et /document/... : on va se planter
						// non -> séparation doc pointant vers le online et locaux
						
						// DISONS NON : 
						doc = localDoc;
						// doc = bkm;
						
						// mod.setDocProperty(bkm, SLVocab.SL_BOOKMARK_OF_PROPERTY, localDoc.getURI()); // DISONS NON
						mod.setDocProperty(doc, SLVocab.TITLE_PROPERTY, title, lang);
						if (comment != null) mod.setDocProperty(doc, SLVocab.COMMENT_PROPERTY, comment, lang);
						if (localDoc_SourceToBeAdded != null) {
							// la source : affectée au doc local 
							mod.addDocProperty(localDoc_SourceToBeAdded, SLVocab.SOURCE_PROPERTY, docOnline.getURI());
							// ou bien au bkm ? bkm source doconline // BEN COMME ON A DIT NON
							// mod.addDocProperty(bkm, SLVocab.SOURCE_PROPERTY, docOnline.getURI());
						}

					} else if (request.getParameter("localDocBtn") != null) {	
						
						// CREER OU PAS UN "BKM" ??? CONTINUONS A DIRE NON
						doc = localDoc;
						// doc = bkm;
						
						// mod.setDocProperty(bkm, SLVocab.SL_BOOKMARK_OF_PROPERTY, localDoc.getURI()); // DISONS NON
						mod.setDocProperty(doc, SLVocab.TITLE_PROPERTY, title, lang);
						if (comment != null) mod.setDocProperty(doc, SLVocab.COMMENT_PROPERTY, comment, lang);

					}
				}

				// b4 2019-03
//				mod.setDocProperty(doc, SLVocab.TITLE_PROPERTY, title, lang);
//				if (comment != null) mod.setDocProperty(doc, SLVocab.COMMENT_PROPERTY, comment, lang);
//				if (localDoc_SourceToBeAdded != null) {
//					mod.addDocProperty(localDoc_SourceToBeAdded, SLVocab.SOURCE_PROPERTY, docuri);
//				}

				
				mod.onNewDoc(doc);

				// 2007-01 (POST REDIRECT)
				// getJsp_Document(doc, request); // documente l'attribut jsp de la request
				docToDisplay = doc;
			} // doc already exists or not

			// POST REDIRECT
			// x = mapping.findForward("continue");
			redirectURL = Util.getContextURL(request) + HTML_Link.docLink(docToDisplay.getURI());
		}



		response.sendRedirect(response.encodeRedirectURL(redirectURL));
		return null;
	} catch (Exception e) {
		return error(mapping, request, e );
	}
} // end execute

/** si le HTMLPageDownload est présent ds le Form_Bookmark, le prend, sinon le calcule.
 *  N'y a-t-il pas un pb si on a déjà fait un HTMLPageDownload sur un autre doc, puis
 *  qu'il n'y en a pas de remis au dernier apple ? Si, surement : il doit faloir remettre
 * à vide le HTMLPageDownload de Form_Bookmark une fois qu'il est consommé. Ceci est fait en fin
 * de l'execute
 * @param bookmarkForm
 * @return
 * @throws MalformedURLException
 * @throws IOException
 */
/*private HTMLPageDownload getDownload(Form_Bookmark bookmarkForm) throws MalformedURLException, IOException {
 // return new HTMLPageDownload(new URL(docuri));
  HTMLPageDownload x = bookmarkForm.getDownload();
  if (x == null) x = new HTMLPageDownload(new URL(bookmarkForm.getDocuri()));
  return x;
  }*/

/*private String getTitle(Form_Bookmark bookmarkForm) throws MalformedURLException, IOException {
 String title = bookmarkForm.getTitle();
 if ((title == null)||("".equals(title))) title = getDownload(bookmarkForm).getTitle();
 // title = getDownload(bookmarkForm).getTitle();
  return title;
 }*/

} // end Action
