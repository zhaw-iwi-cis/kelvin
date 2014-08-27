package ch.zhaw.iwi.cis.kelvin.proxy;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

import javax.security.auth.Subject;

import ch.zhaw.iwi.cis.kelvin.service.Service;

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
	public < T extends Service > T createServiceProxy( Class< T > serviceInterface )
	{
		return (T)Proxy.newProxyInstance( Thread.currentThread().getContextClassLoader(), new Class< ? >[] { serviceInterface },
			new ServiceProxyInvocationHandler( serviceAddress, serviceInterface, subject ) );
	}
}
