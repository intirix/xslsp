package com.intirix.xslsp.html;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

public class TestXsltEngine {

	private XsltEngine engine = new XsltEngine();
	
	@Test
	public void testCopy() throws TransformerException
	{
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		engine.transform("copy", getClass().getResourceAsStream("/html/xsl/copy.xsl"), getClass().getResourceAsStream("/html/xsl/dummy1.xml"), buffer);
		Assert.assertTrue( buffer.size() > 0 );
//		System.out.println( buffer.toString() );
	}
}
