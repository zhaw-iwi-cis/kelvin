package ch.zhaw.iwi.cis.kelvin.framework;

import java.security.Principal;

import javax.security.auth.Subject;


public class BasicAuthPrincipal implements java.security.Principal
{
	private String name;

	public BasicAuthPrincipal( String name )
	{
		super();
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	public static BasicAuthPrincipal getBasicAuthPrincipal( Subject subject )
	{
		BasicAuthPrincipal foundPrincipal = null;
		
		for ( Principal principal : subject.getPrincipals() )
		{
			if ( principal instanceof BasicAuthPrincipal )
			{
				if ( foundPrincipal == null )
				{
					foundPrincipal = (BasicAuthPrincipal)principal;
				}
				else
				{
					throw new RuntimeException( "More than one principal of type " + BasicAuthPrincipal.class.getName() + " found for subject: " + subject );
				}
			}
		}
		
		if ( foundPrincipal == null )
			throw new RuntimeException( "No principal of type " + BasicAuthPrincipal.class.getName() + " found for subject: " + subject );
		
		return foundPrincipal;
	}
}
