package com.intirix.xslsp.html;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface PageBeanFactory
{

	/**
	 * Create a page bean
	 * @param request
	 * @param optional result of any POST actions that were performed
	 * @return
	 * @throws ServletException
	 */
	public PageData createPageBean( HttpServletRequest request, Object actionResult ) throws ServletException;
}
