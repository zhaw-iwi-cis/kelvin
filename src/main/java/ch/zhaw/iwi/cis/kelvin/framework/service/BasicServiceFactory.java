package ch.zhaw.iwi.cis.kelvin.framework.service;

import __java.lang.__Class;


public class BasicServiceFactory implements ServiceFactory
{
	private Class< ? > serviceClass;

	public BasicServiceFactory( Class< ? > serviceClass )
	{
		super();
		this.serviceClass = serviceClass;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > T create()
	{
		T service = (T)__Class.newInstance( serviceClass );
		
		return service;
	}
}
