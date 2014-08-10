package com.intirix.xslsp.html;

/**
 * Translate a uri to an html filename
 * @author jeff
 *
 */
public interface HtmlFilenameTranslator {

	/**
	 * Get the filename of the html/xsl file for the given uri
	 * @param uri
	 * @return
	 */
	public String getHtmlFilename( String uri );
}
