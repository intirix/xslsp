package com.intirix.xslsp.html;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

public class TestXsltTransformation
{

	@Test
	public void testStep() throws IOException, TransformerException
	{
		final XsltEngine engine = new XsltEngine();
		final XsltTransformation step = new XsltTransformation( engine, "/html/xsl/copy.xsl" );
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		step.transform( getClass().getResourceAsStream("/html/xsl/dummy1.xml"), buffer );

		Assert.assertTrue( buffer.size() > 0 );

	}
}
