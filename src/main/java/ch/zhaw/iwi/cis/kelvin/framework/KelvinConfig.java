package ch.zhaw.iwi.cis.kelvin.framework;

import java.net.InetSocketAddress;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;

import __java.lang.__Class;

public abstract class KelvinConfig
{
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
	
	public InetSocketAddress getDatabaseInetSocketAddress()
	{
		return new InetSocketAddress( "0.0.0.0", 1527 );
	}
	
	public InetSocketAddress getWebServerInetSocketAddress()
	{
		return new InetSocketAddress( "0.0.0.0", 8080 );
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
		Reflections reflections = new Reflections( ".*" );
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
