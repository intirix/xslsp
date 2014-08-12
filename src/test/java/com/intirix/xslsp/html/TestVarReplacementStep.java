package com.intirix.xslsp.html;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrLookup;
import org.junit.Assert;
import org.junit.Test;

public class TestVarReplacementStep
{

	@Test
	public void testStep() throws IOException, TransformerException
	{
		final TransformationStep step = new VarReplacementStep( StrLookup.systemPropertiesLookup() );
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		step.transform( IOUtils.toInputStream( "${java.version}" ), buffer );
		Assert.assertEquals( System.getProperty( "java.version" ), buffer.toString() );
	}
}
