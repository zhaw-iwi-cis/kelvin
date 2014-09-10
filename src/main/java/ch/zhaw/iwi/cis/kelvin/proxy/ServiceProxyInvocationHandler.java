package ch.zhaw.iwi.cis.kelvin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import javax.security.auth.Subject;

import ch.zhaw.iwi.cis.kelvin.framework.BasicAuthCredential;
import ch.zhaw.iwi.cis.kelvin.framework.BasicAuthPrincipal;
import ch.zhaw.iwi.cis.kelvin.framework.JavaServiceRequest;
import ch.zhaw.iwi.cis.kelvin.framework.JavaServiceResponse;
import ch.zhaw.iwi.cis.kelvin.framework.JsonServiceRequest;
import ch.zhaw.iwi.cis.kelvin.framework.JsonServiceResponse;

public class ServiceProxyInvocationHandler implements InvocationHandler
{
	private InetSocketAddress serverAddress;
	private Class< ? > serviceInterface;
	private BasicAuthPrincipal principal;
	private BasicAuthCredential credential;
	
	public ServiceProxyInvocationHandler( InetSocketAddress serverAddress, Class< ? > serviceInterface, Subject subject )
	{
		super();

		this.serverAddress = serverAddress;
		this.serviceInterface = serviceInterface;
		principal = BasicAuthPrincipal.getBasicAuthPrincipal( subject );
		credential = BasicAuthCredential.getBasicAuthCredential( subject );
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
	{
		if ( args == null )
			args = new Object[] {};

		JavaServiceRequest javaServiceRequest = new JavaServiceRequest( serviceInterface, method, args );
		JsonServiceRequest jsonServiceRequest = JsonServiceRequest.getJsonServiceRequest( javaServiceRequest );
		JsonServiceResponse jsonServiceResponse = jsonServiceRequest.invoke( serverAddress, principal, credential );
		JavaServiceResponse javaServiceResponse = JavaServiceResponse.getJavaServiceResponse( jsonServiceResponse );
		
		if ( javaServiceResponse.getException() != null )
			throw javaServiceResponse.getException();
		
		return javaServiceResponse.getReturnValue();
	}
}
