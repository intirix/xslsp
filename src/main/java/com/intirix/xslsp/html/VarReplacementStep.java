package com.intirix.xslsp.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 * Perform variable replacement
 * @author jeff
 *
 */
public class VarReplacementStep implements TransformationStep
{
	private final StrLookup varLookup;

	/**
	 * Create a replacement step object
	 * @param varLookup
	 */
	public VarReplacementStep( StrLookup varLookup )
	{
		this.varLookup = varLookup;
	}

	public void transform(InputStream is, OutputStream os) throws IOException, TransformerException
	{
		final String outputString = new StrSubstitutor( varLookup ).replace( IOUtils.toString( is ) );
		os.write( outputString.getBytes() );
	}

}
