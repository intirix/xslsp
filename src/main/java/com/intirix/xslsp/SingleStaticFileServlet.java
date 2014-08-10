package com.intirix.xslsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class SingleStaticFileServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String path;
	
	public SingleStaticFileServlet()
	{
		
	}
	
	public SingleStaticFileServlet( String path )
	{
		this.path = path;
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		IOUtils.copy( getClass().getResourceAsStream( path ), resp.getOutputStream() );
	}
	
	
}
