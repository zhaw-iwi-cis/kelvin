package ch.zhaw.iwi.cis.kelvin.framework.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import __java.lang.__Class;

public class ServiceRegistry
{
	private static ServiceRegistry serviceRegistry;
	
	public static ServiceRegistry getRegistry()
	{
		if ( serviceRegistry == null )
			serviceRegistry = new ServiceRegistry();

		return serviceRegistry;
	}
	
	public synchronized void start()
	{
		Reflections reflections = new Reflections( ".*" );
		Set< Class< ? > > serviceRegistryAgentClasses = reflections.getTypesAnnotatedWith( ServiceRegistryAgent.class );
		
		for ( Class< ? > serviceRegistryAgentClass : serviceRegistryAgentClasses )
		{
			ServiceRegistryAgentInterface agent = (ServiceRegistryAgentInterface)__Class.newInstance( serviceRegistryAgentClass );
			agent.registerServiceFactories( this );
		}
	}
	
	public synchronized void stop()
	{
	}
	
	private Map< String, ServiceFactory > serviceFactoryMap = new HashMap< String, ServiceFactory >();
	private ThreadLocal< Map< String, Object > > serviceMapThreadLocal = new ThreadLocal< Map< String, Object > >();
	
	public synchronized void registerServiceFactory( String name, ServiceFactory factory )
	{
		serviceFactoryMap.put( name, factory );
	}
	
	@SuppressWarnings( "unchecked" )
	public synchronized < T > T getService( String name )
	{
		Map< String, Object > serviceMap = getServiceMap();
		
		T service = (T)serviceMap.get( name );
		
		if ( service == null )
		{
			ServiceFactory factory = serviceFactoryMap.get( name );
			service = factory.create();
			serviceMap.put( name, service );
		}
		
		return service;
	}
	
	public synchronized Set< String > getServiceNamesWithInterface( Class< ? > theInterface )
	{
		Set< String > serviceNamesWithInterface = new HashSet< String >();
		
		for ( Map.Entry< String, ServiceFactory > entry : serviceFactoryMap.entrySet() )
		{
			ServiceFactory factory = entry.getValue();
			Method createMethod = __Class.getMethod( factory.getClass(), "create", (Class< ? >[])null );
			Class< ? > returnType = createMethod.getReturnType();
			
			if ( theInterface.isAssignableFrom( returnType ) )
				serviceNamesWithInterface.add( entry.getKey() );
		}
		
		return serviceNamesWithInterface;
	}
	
	private Map< String, Object > getServiceMap()
	{
		Map< String, Object > serviceMap = serviceMapThreadLocal.get();
		
		if ( serviceMap == null )
		{
			serviceMap = new HashMap< String, Object >();
			serviceMapThreadLocal.set( serviceMap );
		}
		
		return serviceMap;
	}
}
