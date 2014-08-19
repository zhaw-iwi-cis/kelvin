package ch.zhaw.iwi.cis.kelvin.proxy;

import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;

import javax.security.auth.Subject;

import __java.lang.__Class;
import __java.lang.reflect.__Constructor;

public class ServiceProxyManager
{
	private InetSocketAddress serviceAddress;
	private Subject subject;
	
	public ServiceProxyManager( InetSocketAddress serviceAddress, Subject subject )
	{
		super();
		this.serviceAddress = serviceAddress;
		this.subject = subject;
	}

	public static ServiceProxyManager getServiceProxyManager( InetSocketAddress serviceAddress, Subject subject )
	{
		return new ServiceProxyManager( serviceAddress, subject );
	}
	
	@SuppressWarnings( "unchecked" )
	public < T extends ServiceProxy > T createServiceProxy( Class< T > proxyClass )
	{
		Constructor< ? > constructor = __Class.getDeclaredConstructor( proxyClass, InetSocketAddress.class, Subject.class );
		constructor.setAccessible( true );
		T serviceProxy = (T)__Constructor.newInstance( constructor, serviceAddress, subject );
		
		return serviceProxy;
	}
}
