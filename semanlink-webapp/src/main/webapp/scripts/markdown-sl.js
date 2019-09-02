// ATTENTION suppose définie la fct getContextURL() (qui retourne par ex http://127.0.0.1:7080/semanlink)/** * Display a markdown document (identified by uri), * within the div id "md", * replacing relative links (supposed to be relative to uri)  * @param uri uri of the displayed file * @requestURL url of the request, if we want to make relative links relative to it it rather than relative to uri */function displayMarkdown(uri, requestURL) {	// alert("displayMarkdown: " + uri + " window.location.href " + window.location.href);	// what should we use as baseUri when computing relative links?	// Well, uri, the uri of the document.	//	var baseUri = false;//	if (requestURL) {//		baseUri = requestURL;//	} else {//		baseUri = uri;//	}	var baseUri = uri;//	// But when uri is served by a webserver (eg. uri = http://127.0.0.1/~fps/fps/2015/09/adoc.md)//	// then we'll compute the href relatively to it//	// and we'll end up with a href such as semanlink/doc/?uri=http://127.0.0.1/~fps/fps/2015/09/adoc.md//	// It works, but the href is not nice: semanlink/sl/doc/2015/09/adoc.md would be better//	// SO, we may think: if we're already displaying a doc,//	// that is when window.location.href looks like http://127.0.0.1:8080/semanlink/sl/doc/2017/01/thisdoc//	// we could use it as baseUri//	// BUT ONLY for links to .md (or directories), not for//	// images or something else, as the link would point to a sl document//	//	// BEN NON !!! si le lien relatif est juste mydoc.md, on va le prendre par rapport à 2017/01/thisdoc au lieu de /2015/09/adoc.md !!!//	// ca serait ok pour le display de ce doc ds sa page, mais pas par e pour un markdownof//	// Je crois donc 2017-10 qu'il faut virer ça (doit pas etre bien grave, parce que on crée deja //	//	// BEN NON 2 : si window.location.href en doc?uri=..; c fouaré//	// to link to sl/doc/xxx when linking to a md document//	if (uri.endsWith(".md")) { // ca serait ok aussi si uri est une directory, mais on ne sait pas le dire -- wtf? on parle de md ici! je ne sais pas ce à quoi je pensais//		if (window.location.href.indexOf("/sl/doc/") < 0) { // adherence // TODO//			baseUri = window.location.href;			//		}//	}//	alert("baseUri " + baseUri);	var displayMd = function(mdText) {		var md = window.markdownit({			replaceLink: replaceLinkFct(baseUri, requestURL)		});		md.use(window.markdownitReplaceLink);		document.getElementById("md").innerHTML = md.render(mdText);	};	dollarGet(uri, displayMd);}/** * Return the function used by markdownit's ReplaceLink plug in to compute how to replace a link * inside md document * @param baseUri the uri relative links are relative to.  * @param requestURL because for .md files, we want to have them relative to /sl/doc (en fait, un moyen de passer http;//.../sl/doc à la méthode) * baseUri: base for docs supposed to be served by themselves while requestURL is the base for files supposed to be served by semanlink */// NOT OK for folders... (au moins si no webserver) qu'y faire ?// si pas terminé par "/"// par ex dans un doc md// [scikit related](../../2015/10/scikit_python_notes/)(include sample code to read from csv file, training, train set/test set)// donne // http://127.0.0.1:7080/semanlink/document/2015/10/scikit_python_notes// LE PROBLEME, c'est avec les fichers md semanlink qui contiennent des url relatives (pour pouvoir// focntionner en local - ex : des images ds un sous-dossier /ims - MAIS surtout// avec les liens vers du md exprimés en relatif : il faut essayer de retourner un lien en /sl/doc, sinon doc?uri=function replaceLinkFct(baseUri, requestURL) {	// ne pourrait-t-on pas utiliser alert(window.location.href) comme baseUri ??? -- au moins qd elle	// contient /sl/doc/ // TODO A VOIR	// console.log("replaceLinkFct baseUri " + baseUri +"\nrequestURL " + requestURL);	var k = baseUri.lastIndexOf("/");	var baseslash = baseUri.substring(0,k+1); // http://127.0.0.1/~fps/fps/2015/09/	return function (link, env) {		// link, eg.: ../../2015/04/unFichier.md		// if link is absolute, return it		var x = link;		// alert(x + " baseUri: " + baseUri);		if (link.startsWith("http")) {			// cas lien externe : ne rien faire			// cas lien vers semanlink en dur (parce que ds un fichier md dont on veut qu'il marche): on voudrait éventuellement passer à la vraie url semanlink ex 127 -> semanlink.net BOF					} else if (link.startsWith("/")) {						// 2017-10 			// such a link can only be considered absolute wrt to the servlet			// (absolute links inside comments **MUST** be sonsidered as absolute wrt to the servlet)			// (ouais sauf que si on a copié-collé /semanlink/tag/toto, c pas bon)						x = getContextURL() + x;			//			//			// this won't work, but I don't know what could be done.//			// Make it relative to webserver root? to doc folder?//			// Also, we have the case of docs on the disk, such as /Users/fps/adoc//			//			// cf below: will work if /sl/doc or (see elsewhere, if /document or /semanlink) -- HMM SEE WHERE ?					} else {			// relative link eg.: ../../2015/04/unFichier.md			var useBaseSlash = true;			if (requestURL) {				// if .md (or dir), then should be considered as relative to reqestURL (which is a .md)				if ((x.endsWith(".md")) || (x.endsWith("/"))) {  // this probably implies that it is a doc					useBaseSlash = false;				}							}			if (useBaseSlash) {				x = relative2absolute(baseslash, link);			} else {				x = relative2absolute(requestURL, link);			}					}		// console.log("x1: " + x + " was link: " + link);				// x is now absolute		if (!x.startsWith(getContextURL())) {			// don't change it		} else {			var k = x.indexOf("/sl/doc/"); // TODO ATTENTION adherence !!! // 2019-05 : HUM TODO CHECK			if (k < 0) k = x.indexOf("/tag/");			// en toute logique, on devrait decommenter ligne suivante, 			// mais alors, pour un dossier, on retourne "xxx/document/2017/04/", 			// (on ne passe pas ds doc/?uri= et ca ne va pas (/document/ unsupported pour dossiers)			if (k < 0) k = x.indexOf("/document/"); // TODO ATTENTION adherence StaticFileServlet.PATH												if (k >= 0) { // new 2017-09-19				if (getContextURL() + x.substring(k) != x) {					// console.log("BEN SI, UTILE " + getContextURL() + x.substring(k) + " -> " + x);				}				x = getContextURL() + x.substring(k); 							} else {				// for .md and folders, we would like to serve them at /sl/doc				// This is better than nothing				// (assuming it's a doc)				if ((x.endsWith(".md")) || (x.endsWith("/"))) {  // this probably implies that it is a doc							// alert("x : " + x + " - getContextURL() : " + getContextURL());					if (x.startsWith(getContextURL())) { // uniquement si local (ou webserver, il faudrait, mais ne sait pas faire)												// @find doc2markdownHref -- ATTENTION adherence !!!								x = getContextURL() + "/doc/?uri=" + encodeURIComponent(x); // je ne sais pas faire mieux : comment couper l'url au bon endroit ?						// mais en a-t-on encore besoin ? est-ce que ça arrive encore (légitimement) ?						// ben oui : dans les fichiers md qui utilisent des url relatives (afin de focntionner						// aussi en standalone)						// console.log("OUAICHE  " + x);					}									}				}		}		// console.log("xF " + x);		return x;	};}/** * Convert a relative url to an absolute one * Beware, relative supposed to be... relative  * see https://stackoverflow.com/questions/14780350/convert-relative-path-to-absolute-using-javascript */function relative2absolute(base, relative) {    var stack = base.split("/"),        parts = relative.split("/");    stack.pop(); // remove current file name (or empty string)                 // (omit if "base" is the current folder without trailing slash)    for (var i=0; i<parts.length; i++) {        if (parts[i] == ".")            continue;        if (parts[i] == "..")            stack.pop();        else            stack.push(parts[i]);    }    return stack.join("/");}function displayRawMarkdown(uri) {	var displayRawMd = function(mdText) {		document.getElementById("rawmd").value = mdText;	};	dollarGet(uri, displayRawMd);}// in edit mode: display styled and raw markdownfunction displayEditMarkdown(uri) {	var displayBothMd = function(mdText) {		// alert("displayEditMarkdown " + mdText);		document.getElementById("rawmd").value = mdText;		var md = window.markdownit({			replaceLink: replaceLinkFct(uri)		});		md.use(window.markdownitReplaceLink);		document.getElementById("md").innerHTML = md.render(mdText);	};		// pour être sûr qu'on recharge vraiment les données à partir du serveur (?)	// ca a pas l'air de marcher	// safari Failed to load resource: "Request header field Cache-Control" is not allowed by Access-Control-Allow-Headers	// firefox locage d’une requête multiorigines (Cross-Origin Request) : la politique « Same Origin » ne permet pas de consulter la ressource distante située sur http://127.0.0.1/~fps/fps/2017/04/ElasticSearch_Renault.md. Raison : jeton « cache-control » manquant dans l’en-tête CORS « Access-Control-Allow-Headers » du canal de pré-vérification des requêtes CORS.	// dollarGet(uri, displayBothMd, true);	// alert("displayEditMarkdown " + uri);	dollarGet(uri, displayBothMd);}///////** http get  * @param forceReload to avoid taking result in cache */ // ca a pas l'air de marcher// function dollarGet(uri, success, forceReload) {function dollarGet(uri, success) {	var xhr = new XMLHttpRequest();	xhr.open('GET', uri);	xhr.onload = function() {		if (xhr.status === 200) {			if (success) {				success(xhr.responseText);			}    	} else {			alert('Request failed.  Returned status: ' + xhr.status);		}	};//	if (forceReload === true) {//		xhr.setRequestHeader("Cache-Control", "max-age=0");//	}	xhr.send();}function invalidateCache(url) {	var xhr = new XMLHttpRequest();	xhr.open("GET", url, true);	xhr.setRequestHeader("Cache-Control", "max-age=0");	xhr.send();}// ////function Editor(input, preview) {	this.update = function () {		var md = window.markdownit();		var result = md.render(input.value);    		preview.innerHTML = result;	};	input.editor = this;	this.update();};//// SL:COMMENT FIELD//function _getMarkdownit4Comments() {	var baseUri = window.location.href;	var md = window.markdownit({		replaceLink: replaceLinkFct(baseUri)	});	md.use(window.markdownitReplaceLink);	return md;}/** to display sl:comment as markdown */// la jsp met le texte dans la page. Ici, on le reprend comme du markdown// 2018-09: there is a bug : ca ne marche pas pour les docs dans un arbre de kws// (parce que on ne doit pas appeler ceci)function displayCommentAsMarkdown() {	var md = false;	var commentDiv = document.getElementById("slcomment");	if (commentDiv) {		md = _getMarkdownit4Comments();		_handleComment(commentDiv, md, false);	}		var commentsAboutDocs = document.getElementsByClassName("docline_comment");	if (commentsAboutDocs) {		for (var i = 0 ; i < commentsAboutDocs.length ; i++) {			if (!md) md = _getMarkdownit4Comments();			_handleComment(commentsAboutDocs[i], md, true);		}		}}// if text in commentContainer is markdown, replace it by formatted markdown function _handleComment(commentContainer, md, boolean_inline) {	var commentAsText = commentContainer.innerHTML;	commentAsText = replaceAll(commentAsText, "&lt;", "<");//	commentAsText = replaceAll(commentAsText, "&gt;", ">");	//	if ((commentAsText.indexOf("<br/>") > -1)//		|| (commentAsText.indexOf("<br>") > -1)//		|| (commentAsText.indexOf("<a href=\"") > -1)//		|| (commentAsText.indexOf("</object>") > -1)) {    //		// probably it's html, don't change//		commentContainer.innerHTML = commentAsText//		return;//	}	//	if (commentAsText.indexOf("<") > -1) {    //		// probably it's html, don't change//		return;//	}		if (boolean_inline) {		commentContainer.innerHTML = md.renderInline(commentAsText);	} else {		commentContainer.innerHTML = md.render(commentAsText);			}}function replaceAll(str, find, replace) {    return str.replace(new RegExp(find, 'g'), replace);}//////// just a quick hack to open a file in desktop// uri: see docStuff.getLocalCopyLinkfunction desktop_open_hack(uri) {	dollarGet(uri, false);}