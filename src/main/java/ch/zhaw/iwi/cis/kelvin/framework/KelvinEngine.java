package ch.zhaw.iwi.cis.kelvin.framework;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Collections;
import java.util.EnumSet;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import javax.servlet.DispatcherType;

import org.apache.derby.drda.NetworkServerControl;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import __java.net.__URI;
import __java.util.logging.__FileHandler;
import __org.apache.derby.drda.__NetworkServerControl;
import __org.eclipse.jetty.util.component.__AbstractLifeCycle;
import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;
import ch.zhaw.iwi.cis.kelvin.restservice.RestService;

public class KelvinEngine
{
	private static NetworkServerControl derbyServer;
	private static Server webServer;
	private static KelvinEngine kelvinEngine;

	static
	{
		// This only needs to be done once, so am doing it here.
		Runtime.getRuntime().addShutdownHook( new ShutdownThread() );
	}
	
	public static void main( String[] args )
	{
		getEngine().start();
	}
	
	public static KelvinEngine getEngine()
	{
		if ( kelvinEngine == null )
			kelvinEngine = new KelvinEngine();
		
		return kelvinEngine;
	}
	
	public void start()
	{
		setupLogging();
		startDatabase();
		startWebServer();
		startRegistry();
		
		System.out.println( "Server running and ready for requests." );
	}
	
	public void stop()
	{
		stopRegistry();
		stopWebServer();
		
		try
		{
			derbyServer.ping();
			stopDatabase();
		}
		catch ( Exception e )
		{
		}
		
		System.out.println( "Server stopped." );
	}
	
	private static void setupLogging()
	{
		Formatter formatter = new XMLFormatter();
		
		FileHandler applicationHandler = __FileHandler.__new( KelvinConfig.getConfig().getLogDir() + "/application.log" );
		applicationHandler.setLevel( Level.INFO );
		applicationHandler.setFormatter( formatter );
		
		Logger defaultLogger = Logger.getLogger( "" );
		defaultLogger.setLevel( Level.INFO );
		defaultLogger.addHandler( applicationHandler );

		// TODO Find out why this isn't working...
		Logger reflectionsLogger = Logger.getLogger( "org.reflections.Reflections" );
		reflectionsLogger.setLevel( Level.OFF );
		
		FileHandler hibernateSqlHandler = __FileHandler.__new( KelvinConfig.getConfig().getLogDir() + "/hibernate-sql.log" );
		hibernateSqlHandler.setLevel( Level.ALL );
		hibernateSqlHandler.setFormatter( formatter );
		
		Logger hibernateSqlLogger = Logger.getLogger( "org.hibernate.SQL" );
		hibernateSqlLogger.setLevel( Level.ALL );
		hibernateSqlLogger.addHandler( hibernateSqlHandler );
	}
	
	private static void startDatabase()
	{
		InetSocketAddress derbySocketAddress = KelvinConfig.getConfig().getDatabaseInetSocketAddress();
		
		derbyServer = __NetworkServerControl.__new( derbySocketAddress.getAddress(), derbySocketAddress.getPort() );
		__NetworkServerControl.start( derbyServer, null );
		
		System.out.println( "Derby database listening on: " + derbySocketAddress );
	}
	
	private static void startWebServer()
	{
		InetSocketAddress webSocketAddress = KelvinConfig.getConfig().getWebServerInetSocketAddress();

		webServer = new Server( webSocketAddress );

		// Setup session ID manager.
		webServer.setSessionIdManager( new HashSessionIdManager() );
        
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler( getSecurityHandler( getServletContextHandler() ) );
        
        handlers.addHandler( getRequestLogHandler() );
        handlers.addHandler( getResourceHandler() );
		webServer.setHandler( handlers );
        
        // Start the server.
		__AbstractLifeCycle.start( webServer );

		System.out.println( "Jetty servlet engine listening on: " + webSocketAddress );
	}
	
	private static void startRegistry()
	{
		ServiceRegistry.getRegistry().start();
	}
	
	private static ResourceHandler getResourceHandler()
	{
		ResourceHandler handler = new ResourceHandler();
		handler.setDirectoriesListed( true );
		handler.setWelcomeFiles( new String[] { "index.html" } );
		handler.setResourceBase( KelvinConfig.getConfig().getWebDir() );
		
		return handler;
	}

	private static ServletContextHandler getServletContextHandler()
	{
		// Setup servlet context handler
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages( KelvinConfig.getConfig().getApplicationBasePackage() );
		resourceConfig.register( CustomObjectMapperProviderServer.class );
		ServletHolder holder = new ServletHolder( new ServletContainer( resourceConfig ) );
		holder.setInitParameter( "jersey.config.server.provider.classnames", LoggingFilter.class.getName() );
		holder.setInitOrder( 1 );
		ServletContextHandler handler = new ServletContextHandler();
		handler.setContextPath( RestService.SERVICES_BASE );
		handler.addServlet( holder, "/*" );
		handler.addFilter( ThreadLocalFilter.class, "/*", EnumSet.of( DispatcherType.INCLUDE, DispatcherType.REQUEST ) );

		// Setup session handler.
        HashSessionManager manager = new HashSessionManager();
        SessionHandler sessionHandler = new SessionHandler(manager);
        handler.setHandler(sessionHandler);
        
		return handler;
	}
	
	private static RequestLogHandler getRequestLogHandler()
	{
		NCSARequestLog requestLog = new NCSARequestLog( KelvinConfig.getConfig().getLogDir() + "/jetty-yyyy_mm_dd.request.log" );
		requestLog.setRetainDays( 90 );
		requestLog.setAppend( true );
		requestLog.setExtended( false );
		requestLog.setLogTimeZone( "GMT" );

		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog( requestLog );
		
		return requestLogHandler;
	}
	
	private static SecurityHandler getSecurityHandler( Handler delegateHandler )
	{
		URL url = __URI.toURL( new File( KelvinConfig.getConfig().getConfDir() + "/realm.properties" ).toURI() );
		
		HashLoginService loginService = new HashLoginService( "ApplicationRealm", url.toString() );
		webServer.addBean( loginService );

		Constraint constraint = new Constraint( Constraint.__BASIC_AUTH, "user" );
		constraint.setAuthenticate( true );
		
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.setPathSpec( "/*" );
		mapping.setConstraint( constraint );

		ConstraintSecurityHandler handler = new ConstraintSecurityHandler();
		handler.setConstraintMappings( Collections.singletonList( mapping ) );
		handler.setAuthenticator( new BasicAuthenticator() );
		handler.setLoginService( loginService );
		
		handler.setHandler( delegateHandler );
		
		return handler;
	}
	
	private static void stopDatabase()
	{
		__NetworkServerControl.shutdown( derbyServer );
		
		System.out.println( "Derby stopped." );
	}
	
	private static void stopWebServer()
	{
		__AbstractLifeCycle.stop( webServer );
		
		System.out.println( "Jetty stopped." );
	}
	
	private static void stopRegistry()
	{
		ServiceRegistry.getRegistry().stop();
	}
}
