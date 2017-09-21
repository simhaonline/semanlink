// ATTENTION suppose définie la fct getContextURL()/** display a uri supposed to return markdown text, *  replacing relative links (supposed to be relative to uri) */function displayMarkdown(uri) {	var displayMd = function(mdText) {		var md = window.markdownit({			replaceLink: replaceLinkFct(uri)		});		md.use(window.markdownitReplaceLink);		document.getElementById("md").innerHTML = md.render(mdText);	};	dollarGet(uri, displayMd);}/** * Return the function used by markdownit's ReplaceLink plug in to compute how to replace a link * inside md document * @param baseUri the uri relative links are relative to */function replaceLinkFct(baseUri) {	var k = baseUri.lastIndexOf("/");	var baseslash = baseUri.substring(0,k+1); // http://127.0.0.1/~fps/fps/2015/09/	return function (link, env) {		// link, eg.: ../../2015/04/unFichier.md				// if link is absolute, return it		var x = link;		if (link.startsWith("http")) {		} else if (link.startsWith("/")) {			// this won't work, but I don't know what could be done.			// Make it relative to webserver root? to doc folder?			// Also, we have the case of docs on the disk, such as /USers/fps/adoc						// cf below: will work if /sl/doc or (see elsewhere, if /document or /semanlink)					} else {			// relative link eg.: ../../2015/04/unFichier.md or (inside a comment) ../document/2015/04/unFichier.md				x = relative2absolute(baseslash, link); // http://127.0.0.1/~fps/fps/2015/04/unFichier.md					}						//		// pb with that: links to md files served by registered webserver//		// if ((x.endsWith(".md")) && //		//	((x.indexOf("semanlink") > -1) || (x.startsWith("/")))) { // 2eme: pour supporter "/document/2016/..."//		if (x.endsWith(".md")) {////			// @find doc2markdownHref -- ATTENTION adherence !!!////			// x = "/semanlink/doc/?uri=" + encodeURIComponent(x); // ne marche pas sur semanlink.net//			// x = getContextURL() + "/doc/?uri=" + encodeURIComponent(x); // 2017-09-19//			var k = x.indexOf("/sl/doc/");//			if (k < 0) {//				// old way//				x = getContextURL() + "/doc/?uri=" + encodeURIComponent(x);//			} else {//				x = getContextURL() + x.substring(k);//			}//		}				var k = x.indexOf("/sl/doc/"); // TODO ATTENTION adherence !!!		if (k >= 0) { // new 2017-09-19			x = getContextURL() + x.substring(k);					} else {			// pb with that: links to md files served by registered webserver			// if ((x.endsWith(".md")) && 			//	((x.indexOf("semanlink") > -1) || (x.startsWith("/")))) { // 2eme: pour supporter "/document/2016/..."			if (x.endsWith(".md")) {				// @find doc2markdownHref -- ATTENTION adherence !!!				x = getContextURL() + "/doc/?uri=" + encodeURIComponent(x); // je ne sais pas faire mieux : comment couper l'url au bon endroit ?				// mais en a-t-on encore besoin ? est-ce que ça arrive encore (légitimement) ?			}					}		return x;	};}/** * Convert a relative url to an absolute one * Beware, relative supposed to be... relative  * see https://stackoverflow.com/questions/14780350/convert-relative-path-to-absolute-using-javascript */function relative2absolute(base, relative) {    var stack = base.split("/"),        parts = relative.split("/");    stack.pop(); // remove current file name (or empty string)                 // (omit if "base" is the current folder without trailing slash)    for (var i=0; i<parts.length; i++) {        if (parts[i] == ".")            continue;        if (parts[i] == "..")            stack.pop();        else            stack.push(parts[i]);    }    return stack.join("/");}function displayRawMarkdown(uri) {	var displayRawMd = function(mdText) {		document.getElementById("rawmd").value = mdText;	};	dollarGet(uri, displayRawMd);}// in edit mode: display styled and raw markdownfunction displayEditMarkdown(uri) {	var displayBothMd = function(mdText) {		// alert("displayEditMarkdown " + mdText);		document.getElementById("rawmd").value = mdText;		var md = window.markdownit({			replaceLink: replaceLinkFct(uri)		});		md.use(window.markdownitReplaceLink);		document.getElementById("md").innerHTML = md.render(mdText);	};		// pour être sûr qu'on recharge vraiment les données à partir du serveur (?)	// ca a pas l'air de marcher	// safari Failed to load resource: "Request header field Cache-Control" is not allowed by Access-Control-Allow-Headers	// firefox locage d’une requête multiorigines (Cross-Origin Request) : la politique « Same Origin » ne permet pas de consulter la ressource distante située sur http://127.0.0.1/~fps/fps/2017/04/ElasticSearch_Renault.md. Raison : jeton « cache-control » manquant dans l’en-tête CORS « Access-Control-Allow-Headers » du canal de pré-vérification des requêtes CORS.	// dollarGet(uri, displayBothMd, true);	dollarGet(uri, displayBothMd);}///////** http get  * @param forceReload to avoid taking result in cache */ // ca a pas l'air de marcher// function dollarGet(uri, success, forceReload) {function dollarGet(uri, success) {	// alert("dollarGet " + uri);	var xhr = new XMLHttpRequest();	xhr.open('GET', uri);	xhr.onload = function() {		if (xhr.status === 200) {			success(xhr.responseText);    	} else {			alert('Request failed.  Returned status: ' + xhr.status);		}	};//	if (forceReload === true) {//		xhr.setRequestHeader("Cache-Control", "max-age=0");//	}	xhr.send();}function invalidateCache(url) {	var xhr = new XMLHttpRequest();	xhr.open("GET", url, true);	xhr.setRequestHeader("Cache-Control", "max-age=0");	xhr.send();}// ////function Editor(input, preview) {	this.update = function () {		var md = window.markdownit();		var result = md.render(input.value);    		preview.innerHTML = result;	};	input.editor = this;	this.update();};//////function _getMarkdownit4Comments() {	var baseUri = window.location.href;	var md = window.markdownit({		replaceLink: replaceLinkFct(baseUri)	});	md.use(window.markdownitReplaceLink);	return md;}/** to display sl:comment as markdown */// la jsp met le texte dans la page. Ici, on le reprend comme du markdownfunction displayCommentAsMarkdown() {	var md = false;	var commentDiv = document.getElementById("slcomment");	if (commentDiv) {		md = _getMarkdownit4Comments();		_handleComment(commentDiv, md, false);	}		var commentsBoutDOcs = document.getElementsByClassName("docline_comment");	if (commentsBoutDOcs) {		for (var i = 0 ; i < commentsBoutDOcs.length ; i++) {			if (!md) md = _getMarkdownit4Comments();			_handleComment(commentsBoutDOcs[i], md, true);		}		}}// if text in commentContainer is markdown, replace it by formatted markdown function _handleComment(commentContainer, md, boolean_inline) {	var commentAsText = commentContainer.innerHTML;	if (commentAsText.indexOf("<") > -1) {		// probably it's html, don't change		return;	}	if (boolean_inline) {		commentContainer.innerHTML = md.renderInline(commentAsText);	} else {		// il peut y avoir des &gt; et &lt; dans commentAsText		// remplacer les &gt; en > (citation)		commentAsText = replaceAll(commentAsText, "&gt;", ">");		commentContainer.innerHTML = md.render(commentAsText);			}}function replaceAll(str, find, replace) {    return str.replace(new RegExp(find, 'g'), replace);}