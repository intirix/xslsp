package com.intirix.xslsp.html;

import java.io.Serializable;

import org.simpleframework.xml.Default;

@Default
public class PageData implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String actionMessage = "";


	public String getActionMessage()
	{
		return actionMessage;
	}


	public void setActionMessage( String actionMessage )
	{
		this.actionMessage = actionMessage;
	}
	
	


}
