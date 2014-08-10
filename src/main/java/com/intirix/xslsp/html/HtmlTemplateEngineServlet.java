package com.intirix.xslsp.html;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet the invokes the template renderer
 * @author jeff
 *
 */
public class HtmlTemplateEngineServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final Logger log = Logger.getLogger( HtmlTemplateEngineServlet.class );


	/**
	 * resource bundle
	 */
	private final ResourceBundle bundle = ResourceBundle.getBundle( "OpenmmServerUIBundle" );

	/**
	 * Template engine
	 */
	private final HtmlTemplateEngine htmlEngine;

//	private PostActionEngine actionEngine;

	public HtmlTemplateEngineServlet()
	{
		htmlEngine = new HtmlTemplateEngine( bundle );
//		actionEngine = runtime.getActionEngine();
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		/*
		if ( config.getBaseUrl().length() == 0 )
		{
			config.setBaseUrl( req.getRequestURL().toString().replaceFirst( "/html.*", "" ) );
			config.saveToUserDir();
		}
		*/
		
		
		final String uri = req.getRequestURI().replaceFirst( "^" + req.getServletPath(), "" );

		try
		{
			try
			{
				final PageData bean = htmlEngine.createPageBean( uri, req, null );
				htmlEngine.renderPage( uri, bean, resp.getOutputStream() );
			}
			catch ( ClassNotFoundException e )
			{
				htmlEngine.renderPage( uri, new PageData(), resp.getOutputStream() );
			}
		}
		catch ( FileNotFoundException e )
		{
			log.warn( "Could not find file: " + e.toString() );
			resp.sendError( 404 );
		}
		catch ( Exception e )
		{
			log.warn( "Failed to process request", e );
			resp.sendError( 500 );
		}
	}

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		try
		{
			final String action = req.getParameter( "action" );
			/*
			final PostAction actionObject = actionEngine.getAction( action );
			PostActionResult actionResult = null;

			// if the action object exists, then process the action
			if ( actionObject != null )
			{
				actionResult = actionObject.processAction( req );
				
				if ( actionResult != null && actionResult.getRedirectUrl() != null )
				{
					resp.sendRedirect( actionResult.getRedirectUrl() );
					return;
				}
			}

*/

			final String uri = req.getRequestURI().replaceFirst( "^" + req.getServletPath(), "" );

			// create the page bean
			PageData bean = new PageData();

			try
			{
				bean = htmlEngine.createPageBean( uri, req, null );
			}
			catch ( ClassNotFoundException e )
			{
				// ignore
			}

			// transfer the action message to the data bean
			/*
			if ( actionResult != null && actionObject != null )
			{
				bean.setActionMessage( actionResult.getActionMessage() );
			}
			*/

			// render the page
			htmlEngine.renderPage( uri, bean, resp.getOutputStream() );
		}
		catch ( FileNotFoundException e )
		{
			log.warn( "Could not find file: " + e.toString() );
			resp.sendError( 404 );
		}
		catch ( Exception e )
		{
			log.warn( "Failed to process request", e );
			resp.sendError( 500 );
		}
	}




}
