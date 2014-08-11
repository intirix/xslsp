package com.intirix.xslsp.html;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 * Resolve imports out of the classpath
 * @author jeff
 *
 */
public class ClasspathURIResolver implements URIResolver
{
	
	private String basePath;
	
	/**
	 * Create the resolver using a custom base path
	 * @param basePath
	 */
	public ClasspathURIResolver( String basePath )
	{
		this.basePath = basePath;
	}
	
	/**
	 * Create resolver using the default base path
	 */
	public ClasspathURIResolver()
	{
		this( "/html/xsl" );
	}

	public Source resolve( String href, String base ) throws TransformerException
	{
		return new StreamSource( getClass().getResourceAsStream( basePath + '/' + href ) );
	}

}
