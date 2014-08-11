package com.intirix.xslsp.html;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * XSLT Transformation engine
 * 
 * We encapsulate the XSLT transformation into its own class.
 * @author jeff
 *
 */
public class XsltEngine
{

	/**
	 * The uri resolver when xslt files include other files
	 */
	private URIResolver resolver;
	
	
	/**
	 * Get the file resolver
	 * @return
	 */
	public URIResolver getResolver() {
		return resolver;
	}



	/**
	 * Change the URI resolver that gets used when on XSLT file includes another XSLT file
	 * @param resolver
	 */
	public void setResolver(URIResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Create an instance using the default uri resolver
	 */
	public XsltEngine()
	{
		setResolver( new ClasspathURIResolver() );
	}


	/**
	 * Perform a transformation
	 * @param xslName name of the transformation
	 * @param xslIs input stream for the xsl
	 * @param xmlIs input stream for the xml
	 * @param xmlOut output stream for the xml
	 * @throws TransformerException
	 */
	public void transform( String xslName, InputStream xslIs, InputStream xmlIs, OutputStream xmlOut ) throws TransformerException
	{
		final StreamSource xmlSource = new StreamSource( xmlIs );
		final StreamSource xslSource = new StreamSource( xslIs );
		final StreamResult xmlResult = new StreamResult( xmlOut );
		
		final TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
		transFact.setURIResolver( getResolver() );

		final Transformer trans = transFact.newTransformer( xslSource );
		trans.setURIResolver( getResolver() );

		trans.transform( xmlSource, xmlResult );



	}

}
