package ch.zhaw.iwi.cis.kelvin.framework;

import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;

import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;

import __java.lang.__Class;

public abstract class KelvinConfig
{
	public static final String DEFAULT_DATABASE_HOSTNAME = "0.0.0.0";
	public static final int DEFAULT_DATABASE_PORT = 1527;
	public static final String DEFAULT_WEB_SERVER_HOSTNAME = "0.0.0.0";
	public static final int DEFAULT_WEB_SERVER_PORT = 8080;
	public static final String DEFAULT_TEST_CLIENT_HOSTNAME = "localhost";
	public static final int DEFAULT_TEST_CLIENT_PORT = 8080;
	public static final int DEFAULT_TEST_CLIENT_PORT_WITH_PROXY = 8888;
	public static final String DEFAULT_TEST_CLIENT_USER_NAME = "testuser";
	public static final String DEFAULT_TEST_CLIENT_USER_PASSWORD = "testpassword";
	
	public static final String APPLICATION_HOME = KelvinConfig.class.getPackage().getName() + ".applicationHome";
	public static final String BIN = "/bin";
	public static final String CONF = "/conf";
	public static final String DB = "/db";
	public static final String LIB = "/lib";
	public static final String LOG = "/log";
	public static final String PLUGIN = "/plugin";
	public static final String WEB = "/web";
	
	public final String getApplicationHome()
	{
		return System.getProperty( APPLICATION_HOME );
	}
	
	public final String getBinDir()
	{
		return getApplicationHome() + BIN;
	}
	
	public final String getConfDir()
	{
		return getApplicationHome() + CONF;
	}
	
	public final String getDbDir()
	{
		return getApplicationHome() + DB;
	}
	
	public final String getLibDir()
	{
		return getApplicationHome() + LIB;
	}
	
	public final String getLogDir()
	{
		return getApplicationHome() + LOG;
	}
	
	public final String getWebDir()
	{
		// TODO Make webBase configuration dependent: alternative is ""
		String webBase = "/../../../src";
		return getApplicationHome() + webBase + WEB;
	}
	
	public abstract String getApplicationBasePackage();
	
	public InetSocketAddress getDatabaseAddress()
	{
		return new InetSocketAddress( DEFAULT_DATABASE_HOSTNAME, DEFAULT_DATABASE_PORT );
	}
	
	public InetSocketAddress getWebServerAddress()
	{
		return new InetSocketAddress( DEFAULT_WEB_SERVER_HOSTNAME, DEFAULT_WEB_SERVER_PORT );
	}
	
	public InetSocketAddress getTestClientAddress()
	{
		return new InetSocketAddress( DEFAULT_TEST_CLIENT_HOSTNAME, DEFAULT_TEST_CLIENT_PORT );
	}
	
	public Subject getTestClientSubject()
	{
		Set< Principal > principals = new HashSet< Principal >();
		principals.add( new BasicAuthPrincipal( DEFAULT_TEST_CLIENT_USER_NAME ) );
		
		Set< Object > pubCredentials = new HashSet< Object >();
		Set< Object > privCredentials = new HashSet< Object >();
		privCredentials.add( new BasicAuthCredential( DEFAULT_TEST_CLIENT_USER_PASSWORD ) );
		
		Subject subject = new Subject( true, principals, pubCredentials, privCredentials );
		
		return subject;
	}
	
	public abstract String getPersistenceUnitName();
	
	private static KelvinConfig config;

	public static KelvinConfig getConfig()
	{
		if ( config == null )
			config = createConfig();
		
		return config;
	}
	
	@SuppressWarnings( "unchecked" )
	private static KelvinConfig createConfig()
	{
		Reflections reflections = ReflectionsUtil.getReflections( ".*" );
		Set< Class< KelvinConfig > > configClasses = (Set< Class< KelvinConfig > >)(Object)reflections.getTypesAnnotatedWith( ApplicationConfig.class );
		
		if ( configClasses.size() < 1 )
			throw new RuntimeException( "No " + ApplicationConfig.class.getName() + " class found." );
		
		if ( configClasses.size() > 1 )
			throw new RuntimeException( "More than one " + ApplicationConfig.class.getName() + " class found: " + StringUtils.join( configClasses, ", " ) );
		
		Class< KelvinConfig > configClass = configClasses.iterator().next();
		KelvinConfig config = __Class.newInstance( configClass );

		return config;
	}
}
