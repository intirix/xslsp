package com.intirix.xslsp.html;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

public class TestClasspathURIResolver {

	@Test
	public void testDefault() throws TransformerException
	{
		final ClasspathURIResolver obj = new ClasspathURIResolver();
		Assert.assertNotNull( obj.resolve("copy.xsl", "") );
	}

	@Test
	public void testCustom() throws TransformerException
	{
		final ClasspathURIResolver obj = new ClasspathURIResolver( "/html/xsl" );
		Assert.assertNotNull( obj.resolve("copy.xsl", "") );
	}
}
