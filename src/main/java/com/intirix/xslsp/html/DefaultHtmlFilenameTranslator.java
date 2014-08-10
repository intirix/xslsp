package com.intirix.xslsp.html;

/**
 * Default implementation that looks for xsl files in /html/pages
 * @author jeff
 *
 */
public class DefaultHtmlFilenameTranslator implements HtmlFilenameTranslator {
	
	private final String basePath;
	
	public DefaultHtmlFilenameTranslator( String basePath )
	{
		this.basePath = basePath;
	}
	
	public DefaultHtmlFilenameTranslator()
	{
		this( "/html/pages" );
	}

	public String getHtmlFilename( String uri )
	{
		String filename = uri;
		if ( filename.endsWith( "/" ) )
		{
			filename = filename + "index.html";
		}

		if ( !filename.startsWith( "/" ) )
		{
			filename = '/' + filename;
		}

		filename = filename.replace( ".html", ".xsl" );

		filename = basePath + filename;

		return filename;
	}

}
