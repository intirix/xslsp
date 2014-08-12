package com.intirix.xslsp.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

public class TestTransformationPipeline
{

	/**
	 * Test a pipeline with 3 steps in it
	 * @throws IOException 
	 * @throws TransformerException 
	 */
	@Test
	public void testPipeline() throws IOException, TransformerException
	{
		final TransformationPipeline pipeline = new TransformationPipeline();
		pipeline.addStep( new CopyTransformation() );
		pipeline.addStep( new CopyTransformation() );
		pipeline.addStep( new CopyTransformation() );
		
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		pipeline.transform( new ByteArrayInputStream( "Test String".getBytes() ), output);
		Assert.assertEquals( "Test String", output.toString() );
	}
}
