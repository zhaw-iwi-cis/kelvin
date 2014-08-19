package ch.zhaw.iwi.cis.kelvin.framework;

import java.io.Serializable;

import javax.security.auth.Subject;

public class BasicAuthCredential implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String password;

	public BasicAuthCredential( String password )
	{
		super();
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public static BasicAuthCredential getBasicAuthCredential( Subject subject )
	{
		BasicAuthCredential foundCredential = null;
		
		for ( Object credential : subject.getPrivateCredentials() )
		{
			if ( credential instanceof BasicAuthCredential )
			{
				if ( foundCredential == null )
				{
					foundCredential = (BasicAuthCredential)credential;
				}
				else
				{
					throw new RuntimeException( "More than one credential of type " + BasicAuthCredential.class.getName() + " found for subject: " + subject );
				}
			}
		}
		
		if ( foundCredential == null )
			throw new RuntimeException( "No credential of type " + BasicAuthCredential.class.getName() + " found for subject: " + subject );
		
		return foundCredential;
	}
}
