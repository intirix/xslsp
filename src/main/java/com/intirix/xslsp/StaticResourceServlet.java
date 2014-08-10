package com.intirix.xslsp;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * Serve static files out of the webjar resources folders
 * @author jeff
 *
 */
public class StaticResourceServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String classPathPrefix;
	
	public StaticResourceServlet( String classPathPrefix )
	{
		this.classPathPrefix = classPathPrefix;
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		final String uri = req.getRequestURI().replaceFirst( "^" + req.getServletPath(), "" );
		final String path = classPathPrefix + uri;
		
		final InputStream is = getClass().getResourceAsStream( path );
		if ( is == null )
		{
			resp.sendError( 404 );
		}
		else
		{
			if ( uri.endsWith( ".js" ) )
			{
				resp.setContentType( "text/javascript" );
			}
			else if ( uri.endsWith( ".css" ) )
			{
				resp.setContentType( "text/css" );
			}
			IOUtils.copy( is, resp.getOutputStream() );
		}
	}
	
	

}
