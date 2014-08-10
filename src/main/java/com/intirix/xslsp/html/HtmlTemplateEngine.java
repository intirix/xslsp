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
import org.apache.commons.lang.text.StrSubstitutor;
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

	public HtmlTemplateEngine( ResourceBundle bundle )
	{
		varLookup = new ResourceBundleLookup( bundle );
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
		if ( uri.endsWith( ".phtml" ) )
		{
			ext = "phtml";
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
				final String htmlFilename = getHtmlFilename( uri );
				log.debug( "Rendering " + uri + " with " + htmlFilename );

				final InputStream xslIs = getClass().getResourceAsStream( htmlFilename );
				if ( xslIs == null )
				{
					throw new FileNotFoundException( htmlFilename );
				}

				if ( "phtml".equals( ext ) )
				{
					final ByteArrayOutputStream buffer = new ByteArrayOutputStream( 1024 );
					serializer.write( pageBean, buffer );

					xsltEngine.transform( htmlFilename, xslIs, new ByteArrayInputStream( buffer.toByteArray() ), out );
				}
				else if ( "html".equals( ext ) )
				{
					final long t1 = System.currentTimeMillis();
					final ByteArrayOutputStream buffer = new ByteArrayOutputStream( 1024 );
					serializer.write( pageBean, buffer );
					final long t2 = System.currentTimeMillis();

					byte[] xml = buffer.toByteArray();
					buffer.reset();

					xsltEngine.transform( htmlFilename, xslIs, new ByteArrayInputStream( xml ), buffer );
					final long t3 = System.currentTimeMillis();

					xml = buffer.toByteArray();
					buffer.reset();

					xsltEngine.transform( "taglib.xsl", getClass().getResourceAsStream( "/html/xsl/taglib.xsl" ), new ByteArrayInputStream( xml ), buffer );
					final long t4 = System.currentTimeMillis();

					final String outputString = new StrSubstitutor( varLookup ).replace( buffer.toString() );
					out.write( outputString.getBytes() );
					
					final long t5 = System.currentTimeMillis();
					
					final long dt1 = t2 - t1;
					final long dt2 = t3 - t2;
					final long dt3 = t4 - t3;
					final long dt4 = t5 - t4;
					log.debug( "Rendered " + uri + ": ser=" + dt1 + "ms, genXml=" + dt2 + "ms, genHtml=" + dt3 + "ms, out=" + dt4 + "ms" );


				}
			}

			/*

			if ( "html".equals( ext ) || "phtml".equals( ext ) )
			{
				int passes = 2;
				if ( "phtml".equals( ext ) )
				{
					passes = 1;
				}

				final ByteArrayOutputStream buffer = new ByteArrayOutputStream( 1024 );
				serializer.write( pageBean, buffer );

				final String htmlFilename = getHtmlFilename( uri );
				log.debug( "Rendering " + uri + " with " + htmlFilename );
				final InputStream xslIs = getClass().getResourceAsStream( htmlFilename );
				if ( xslIs == null )
				{
					throw new FileNotFoundException( htmlFilename );
				}

				final StreamSource xmlSource = new StreamSource( new ByteArrayInputStream( buffer.toByteArray() ) );
				final StreamSource xslSource = new StreamSource( xslIs );

				buffer.reset();
				final StreamResult xmlResult = new StreamResult( buffer );

				final TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
				transFact.setURIResolver( new ClasspathURIResolver() );

				final Transformer trans = transFact.newTransformer( xslSource );
				trans.setURIResolver( new ClasspathURIResolver() );

				trans.setParameter( "passes", passes );
				trans.transform( xmlSource, xmlResult );

				final String outputString = new StrSubstitutor( varLookup ).replace( buffer.toString() );
				out.write( outputString.getBytes() );
			}
			else if ( "xml".equals( ext ) )
			{
				serializer.write( pageBean, out );
			}
			 */

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

	/**
	 * Get the filename of the html document
	 * @param uri
	 * @return
	 */
	private String getHtmlFilename( String uri )
	{
		String filename = uri;
		if ( filename.endsWith( "/" ) )
		{
			filename = filename + "index.html";
		}

		if ( !filename.startsWith( "/" ) )
		{
			filename = '/' + filename;
		}

		filename = filename.replace( ".phtml", ".xsl" );
		filename = filename.replace( ".html", ".xsl" );

		filename = "/html/pages" + filename;

		return filename;
	}

}
