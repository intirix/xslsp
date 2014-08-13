package com.intirix.xslsp;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Test;

public class TestRootRedirectServlet {

	@Test
	public void test1() throws ServletException, IOException
	{
		final RootRedirectServlet servlet = new RootRedirectServlet();
		
		final ServletConfig config = EasyMock.createMock( ServletConfig.class );
		EasyMock.expect( config.getInitParameter( "redirectUri") ).andReturn( "/test" );
		EasyMock.replay( config );
		
		final HttpServletResponse resp = EasyMock.createMock( HttpServletResponse.class );
		resp.sendRedirect( "/test" );
		EasyMock.expectLastCall();
		EasyMock.replay( resp );
		
		servlet.init( config );
		servlet.doGet( null, resp );

	}
}
