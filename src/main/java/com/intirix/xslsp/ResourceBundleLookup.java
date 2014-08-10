package com.intirix.xslsp;

import java.util.ResourceBundle;

import org.apache.commons.lang.text.StrLookup;

/**
 * Lookup variables based off of a resource bundle
 * @author jeff
 *
 */
public class ResourceBundleLookup extends StrLookup
{
	private final ResourceBundle bundle;
	
	/**
	 * Create instance
	 * @param bundle
	 */
	public ResourceBundleLookup( ResourceBundle bundle )
	{
		this.bundle = bundle;
	}

	@Override
	public String lookup( String var )
	{
		if ( bundle == null )
		{
			return var;
		}
		return bundle.getString( var );
	}

}
