package com.intirix.xslsp.html;

import org.junit.Assert;
import org.junit.Test;

public class TestDefaultHtmlFilenameTranslator {

	@Test
	public void testDefault()
	{
		final DefaultHtmlFilenameTranslator obj = new DefaultHtmlFilenameTranslator();
		Assert.assertEquals( "/html/pages/testa/testb.xsl", obj.getHtmlFilename("/testa/testb.html") );
	}
	
	@Test
	public void testCustom()
	{
		final DefaultHtmlFilenameTranslator obj = new DefaultHtmlFilenameTranslator( "/new/root" );
		Assert.assertEquals( "/new/root/testa/testb.xsl", obj.getHtmlFilename("/testa/testb.html") );
	}
}
