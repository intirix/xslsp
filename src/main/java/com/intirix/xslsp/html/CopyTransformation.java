package com.intirix.xslsp.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;

/**
 * Identity transformation
 * @author jeff
 *
 */
public class CopyTransformation implements TransformationStep {

	public void transform(InputStream is, OutputStream os) throws IOException, TransformerException
	{
		IOUtils.copy( is, os );
	}

}
