package com.intirix.xslsp.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

/**
 * Step in the transformation process
 * @author jeff
 *
 */
public interface TransformationStep {

	/**
	 * Perform a transformation
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public void transform( InputStream is, OutputStream os ) throws IOException, TransformerException;
}
