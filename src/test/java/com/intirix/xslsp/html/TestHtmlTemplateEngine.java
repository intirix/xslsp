package com.intirix.xslsp.html;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import com.intirix.xslsp.html.HtmlTemplateEngine;

public class TestHtmlTemplateEngine
{

	/*
	@Test
	public void testIndex() throws Exception
	{
		final HtmlTemplateEngine engine = new HtmlTemplateEngine(null);
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream( 1024 );
		engine.renderPage( "index.html", "TEST", buffer );
		Assert.assertTrue( buffer.size() > 0 );
	}
	*/
	
	@Test
	public void testMissingXsl() throws Exception
	{
		final HtmlTemplateEngine engine = new HtmlTemplateEngine(null);
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream( 1024 );
		try
		{
			engine.renderPage( "missing.html", "TEST", buffer );
			Assert.fail( "Render should have thrown exception" );
		}
		catch ( FileNotFoundException e )
		{
			// Pass
		}
	}
	
	@Test
	public void testgetPageBeanClassName()
	{
		final HtmlTemplateEngine engine = new HtmlTemplateEngine(null);
		Assert.assertEquals( "com.intirix.openmm.server.ui.html.pages.html.IndexBeanFactory", engine.getPageBeanClassName( "/html/index.html" ) );
		Assert.assertEquals( "com.intirix.openmm.server.ui.html.pages.html.test.IndexBeanFactory", engine.getPageBeanClassName( "/html/test/index.html" ) );
		Assert.assertEquals( "com.intirix.openmm.server.ui.html.pages.html.test.FileBeanFactory", engine.getPageBeanClassName( "/html/test/file.html" ) );

	}
}
