package com.intirix.xslsp;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirect the root to the html site
 * @author jeff
 *
 */
public class RootRedirectServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String redirectUri;
	
	

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		resp.sendRedirect( redirectUri );
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		setRedirectUri( config.getInitParameter( "redirectUri" ) );
	}


	


}
