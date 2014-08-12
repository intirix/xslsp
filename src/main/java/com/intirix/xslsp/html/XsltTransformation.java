package com.intirix.xslsp.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

/**
 * Perform an xsl transformation as part of the transformation step
 * @author jeff
 *
 */
public class XsltTransformation implements TransformationStep {
	
	private XsltEngine xsltEngine;
	
	private String filename;
	
	/**
	 * Create an xsl transformation step
	 * @param xsltEngine
	 * @param filename
	 */
	public XsltTransformation( XsltEngine xsltEngine, String filename )
	{
		this.xsltEngine = xsltEngine;
		this.filename = filename;
	}
	
	public void transform( InputStream is, OutputStream os ) throws IOException, TransformerException
	{
		xsltEngine.transform( filename, getClass().getResourceAsStream(filename), is, os );
	}

}
