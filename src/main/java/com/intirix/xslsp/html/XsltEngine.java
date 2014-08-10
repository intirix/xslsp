package com.intirix.xslsp.html;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XsltEngine
{


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
		transFact.setURIResolver( new ClasspathURIResolver() );

		final Transformer trans = transFact.newTransformer( xslSource );
		trans.setURIResolver( new ClasspathURIResolver() );

		trans.transform( xmlSource, xmlResult );



	}

}
