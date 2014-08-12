package com.intirix.xslsp.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

/**
 * The pipeline of transformations
 * @author jeff
 *
 */
public class TransformationPipeline implements TransformationStep {
	
	private final List< TransformationStep > steps = new ArrayList< TransformationStep >( 4 );
	
	/**
	 * Add a transformation step
	 * @param step
	 */
	public void addStep( TransformationStep step )
	{
		steps.add( step );
	}

	public void transform(InputStream is, OutputStream os) throws IOException, TransformerException
	{
		ByteArrayOutputStream buffer1 = new ByteArrayOutputStream();
		
		// iterate over every step
		for ( int i = 0; i < steps.size(); i++ )
		{
			final TransformationStep step = steps.get( i );

			// use the argument for the first iteration
			// use the buffer for all other iterations
			InputStream bin = is;
			if ( i > 0 )
			{
				bin = new ByteArrayInputStream( buffer1.toByteArray() );
			}
			
			// use the buffer for all but the last iteration
			// use the argument for the last iteration
			OutputStream bout = os;
			if ( i < steps.size() - 1 )
			{
				buffer1.reset();
				bout = buffer1;
			}
			
			step.transform(bin, bout);
		}
	}

}
