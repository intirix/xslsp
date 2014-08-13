package com.intirix.xslsp.html;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	 * Template engine
	 */
	private final HtmlTemplateEngine htmlEngine;

	public HtmlTemplateEngineServlet()
	{
		htmlEngine = new HtmlTemplateEngine();
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		
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

			final String uri = req.getRequestURI().replaceFirst( "^" + req.getServletPath(), "" );

			// create the page bean
			PageData bean = new PageData();

			try
			{
				bean = htmlEngine.createPageBean( uri, req, req.getAttribute( "postResult" ) );
			}
			catch ( ClassNotFoundException e )
			{
				// ignore
			}

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
