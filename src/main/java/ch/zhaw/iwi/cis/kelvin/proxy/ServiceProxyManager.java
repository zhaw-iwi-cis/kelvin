package ch.zhaw.iwi.cis.kelvin.proxy;

import java.lang.reflect.Constructor;

import __java.lang.__Class;
import __java.lang.reflect.__Constructor;

public class ServiceProxyManager
{
	public static final String HOST_NAME = "localhost";
	public static final int PORT = 8080;
	public static final String USER_NAME = "john";
	public static final String PASSWORD = "john";

	@SuppressWarnings( "unchecked" )
	public static < T extends ServiceProxy > T createServiceProxy( Class< T > proxyClass )
	{
		Constructor< ? > constructor = __Class.getDeclaredConstructor( proxyClass, String.class, int.class, String.class, String.class );
		constructor.setAccessible( true );
		T serviceProxy = (T)__Constructor.newInstance( constructor, HOST_NAME, PORT, USER_NAME, PASSWORD );
		
		return serviceProxy;
	}
}
