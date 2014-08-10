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

	public Source resolve( String arg0, String arg1 ) throws TransformerException
	{
		return new StreamSource( getClass().getResourceAsStream( "/html/xsl/" + arg0 ) );
	}

}
