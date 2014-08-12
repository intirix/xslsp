package com.intirix.xslsp.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.text.StrLookup;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.intirix.xslsp.ResourceBundleLookup;

/**
 * Xsl/Html template engine that reads files out of the classpath
 * @author jeff
 *
 */
public class HtmlTemplateEngine
{

	private final Logger log = Logger.getLogger( HtmlTemplateEngine.class );

	private final StrLookup varLookup;

	private XsltEngine xsltEngine = new XsltEngine();
	
	private HtmlFilenameTranslator htmlFilenameTranslator = new DefaultHtmlFilenameTranslator();

	public HtmlTemplateEngine( ResourceBundle bundle )
	{
		varLookup = new ResourceBundleLookup( bundle );
	}
	
	

	public HtmlFilenameTranslator getHtmlFilenameTranslator() {
		return htmlFilenameTranslator;
	}



	public void setHtmlFilenameTranslator(
			HtmlFilenameTranslator htmlFilenameTranslator) {
		this.htmlFilenameTranslator = htmlFilenameTranslator;
	}



	/**
	 * Create the page bean
	 * @param uri
	 * @param req
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ServletException
	 * @throws ClassCastException
	 */
	public PageData createPageBean( String uri, HttpServletRequest req, Object actionResult ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException, ClassCastException
	{
		final Class< ? > klass = Class.forName( getPageBeanClassName( uri ) );
		log.debug( "Creating bean for " + uri + " using " + klass.getName() );
		final long t1 = System.currentTimeMillis();
		final PageBeanFactory beanFactory = (PageBeanFactory)klass.newInstance();
		final PageData pageBean = beanFactory.createPageBean( req, actionResult );
		final long t2 = System.currentTimeMillis();
		final long dt = t2 - t1;
		log.debug( "Finished creating bean for " + uri + " using " + klass.getName() + " in " + dt + "ms" );
		return pageBean;

	}

	/**
	 * Render the page to the output stream
	 * @param uri
	 * @param pageBean
	 * @param out
	 * @throws Exception
	 */
	public void renderPage( String uri, Object pageBean, OutputStream out ) throws Exception
	{
		String ext = "html";
		if ( uri.endsWith( ".xml" ) )
		{
			ext = "xml";
		}

		if ( pageBean != null )
		{
			final Serializer serializer = new Persister();

			if ( "xml".equals( ext ) )
			{
				serializer.write( pageBean, out );
			}
			else
			{
				final String htmlFilename = htmlFilenameTranslator.getHtmlFilename( uri );
				log.debug( "Rendering " + uri + " with " + htmlFilename );

				// verify that the html file exists
				final InputStream xslIs = getClass().getResourceAsStream( htmlFilename );
				if ( xslIs == null )
				{
					throw new FileNotFoundException( htmlFilename );
				}
				
				// serialize the bean
				final ByteArrayOutputStream buffer = new ByteArrayOutputStream( 1024 );
				serializer.write( pageBean, buffer );

				// execute the transformations
				final TransformationPipeline pipeline = new TransformationPipeline();
				pipeline.addStep( new XsltTransformation( xsltEngine, htmlFilename ) );
				pipeline.addStep( new XsltTransformation( xsltEngine, "/html/xsl/taglib.xsl" ) );
				pipeline.addStep( new VarReplacementStep( varLookup ) );
				pipeline.transform( new ByteArrayInputStream( buffer.toByteArray() ), out);

			}

		}

	}

	/**
	 * Get the name of the class that will create the page bean
	 * @param uri
	 * @return
	 */
	String getPageBeanClassName( String uri )
	{
		String className = uri;
		if ( className.endsWith( "/" ) )
		{
			className = className + "index";
		}

		if ( !className.startsWith( "/" ) )
		{
			className = '/' + className;
		}

		className = className.replace( ".phtml", "" );
		className = className.replace( ".html", "" );
		className = className.replace( ".xml", "" );

		className = className.replace( '/', '.' );

		final int lastDot = className.lastIndexOf( '.' );
		className = className.substring( 0, lastDot + 1 ) + Character.toUpperCase( className.charAt( lastDot + 1 ) ) + className.substring( lastDot + 2 );

		className = "com.intirix.openmm.server.ui.html.pages" + className + "BeanFactory";

		return className;
	}


}
