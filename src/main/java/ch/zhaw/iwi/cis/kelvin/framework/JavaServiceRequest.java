package ch.zhaw.iwi.cis.kelvin.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import __java.lang.reflect.__InvocationTargetException;
import __java.lang.reflect.__Method;
import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;

public class JavaServiceRequest extends ServiceRequest
{
	public static Logger logger = Logger.getLogger( JavaServiceRequest.class.getName() );
	
	private Class< ? > serviceInterface;
	private Object serviceInstance;
	private Method serviceMethod;
	private Object[] serviceArgs;
	
	public JavaServiceRequest( Class< ? > serviceInterface, Method serviceMethod, Object[] serviceArgs )
	{
		super();
		this.serviceInterface = serviceInterface;
		this.serviceMethod = serviceMethod;
		this.serviceArgs = serviceArgs;
	}
	
	public JavaServiceRequest( Object serviceInstance, Method serviceMethod, Object[] serviceArgs )
	{
		super();
		this.serviceInstance = serviceInstance;
		this.serviceMethod = serviceMethod;
		this.serviceArgs = serviceArgs;
	}
	
	public static JavaServiceRequest getJavaServiceRequest( JsonServiceRequest jsonServiceRequest )
	{
		Object serviceInstance = getService( jsonServiceRequest.getServiceName() );
		Method serviceMethod = getServiceMethod( serviceInstance, jsonServiceRequest.getServiceMethod() );
		Object[] serviceArgs = getServiceArgs( jsonServiceRequest.getServiceRequestPayload() );
		
		return new JavaServiceRequest( serviceInstance, serviceMethod, serviceArgs );
	}

	private static Object getService( String serviceName )
	{
		Object service = ServiceRegistry.getRegistry().getService( serviceName );
		
		if ( service == null )
			throw new RuntimeException( "No service found with name: " + serviceName );

		return service;
	}
	
	private static Method getServiceMethod( Object serviceInstance, String serviceMethod )
	{
		List< Method > matchingMethods = new ArrayList< Method >();
		
		for ( Method method : serviceInstance.getClass().getMethods() )
			if ( method.getName().equals( serviceMethod ) )
				matchingMethods.add( method );
		
		if ( matchingMethods.isEmpty() )
			throw new RuntimeException( "No service method found with name: " + serviceMethod );
		
		if ( matchingMethods.size() > 1 )
			throw new RuntimeException( "Mutliple (overloaded) service methods found with name: " + serviceMethod );
		
		return matchingMethods.get( 0 );
	}

	private static Object[] getServiceArgs( String serviceRequestPayloadAsString )
	{
		ServiceRequestPayload serviceRequestPayload = null;
		
		if ( serviceRequestPayloadAsString.isEmpty() )
			serviceRequestPayload = new ServiceRequestPayload();
		else
			serviceRequestPayload = readValue( serviceRequestPayloadAsString, ServiceRequestPayload.class );
		
		return serviceRequestPayload.getServiceArgs().toArray( new Object[] {} );
	}

	public JavaServiceResponse invoke()
	{
		JavaServiceResponse serviceResponse = null;
		
		try
		{
			Object retVal = __Method.invoke( serviceMethod, serviceInstance, serviceArgs );
			serviceResponse = new JavaServiceResponse( retVal );
		}
		catch ( __InvocationTargetException e )
		{
			serviceResponse = new JavaServiceResponse( createWrappingException( e.getCause() ) );
		}
		catch ( Throwable e )
		{
			serviceResponse = new JavaServiceResponse( createWrappingException( e ) );
		}
		
		return serviceResponse;
	}
	
	public static Exception createWrappingException( Throwable t )
	{
		logger.log( Level.SEVERE, "Exception caught while invoking service method.", t );

		RuntimeException exception = new RuntimeException(
			"Exception caught while invoking service method (see " + KelvinConfig.getConfig().getLogDir() + " for more details). Original message: " + t.getMessage()
			);

		return exception;
	}
	
	public Object getServiceInstance()
	{
		return serviceInstance;
	}

	public void setServiceInstance( Object serviceInstance )
	{
		this.serviceInstance = serviceInstance;
	}

	public Method getServiceMethod()
	{
		return serviceMethod;
	}

	public void setServiceMethod( Method serviceMethod )
	{
		this.serviceMethod = serviceMethod;
	}

	public Object[] getServiceArgs()
	{
		return serviceArgs;
	}

	public void setServiceArgs( Object[] serviceArgs )
	{
		this.serviceArgs = serviceArgs;
	}
	
	public Class< ? > getServiceInterface()
	{
		return serviceInterface;
	}

	public void setServiceInterface( Class< ? > serviceInterface )
	{
		this.serviceInterface = serviceInterface;
	}
}
