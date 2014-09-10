package ch.zhaw.iwi.cis.kelvin.framework;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import __java.io.__Writer;
import __java.net.__HttpURLConnection;
import __java.net.__URL;
import __java.net.__URLConnection;
import __javax.servlet.__ServletRequest;
import __org.apache.commons.io.__IOUtils;

public class JsonServiceRequest extends ServiceRequest
{
	private String serviceName;
	private String serviceMethod;
	private String serviceRequestPayload;

	private JsonServiceRequest( String serviceName, String serviceMethod, String serviceRequestPayload )
	{
		super();
		this.serviceName = serviceName;
		this.serviceMethod = serviceMethod;
		this.serviceRequestPayload = serviceRequestPayload;
	}

	public static JsonServiceRequest getJsonServiceRequest( JavaServiceRequest javaServiceRequest )
	{
		String serviceName = javaServiceRequest.getServiceInterface().getSimpleName();
		String serviceMethod = javaServiceRequest.getServiceMethod().getName();
		String serviceRequestPayload = writeValueAsString( new ServiceRequestPayload( javaServiceRequest.getServiceArgs() ) );

		return new JsonServiceRequest( serviceName, serviceMethod, serviceRequestPayload );
	}
	
	public static JsonServiceRequest getJsonServiceRequest( HttpServletRequest servletRequest )
	{
		List< String > pathElementsAsList = getServiceNameAndMethod( servletRequest );
		String serviceName = pathElementsAsList.get( 0 );
		String serviceMethod = pathElementsAsList.get( 1 );
		String serviceRequestPayload = getServiceRequestPayload( servletRequest );
		
		return new JsonServiceRequest( serviceName, serviceMethod, serviceRequestPayload );
	}
	
	private static List< String > getServiceNameAndMethod( HttpServletRequest servletRequest )
	{
		String pathInfo = servletRequest.getPathInfo();
		String[] pathElements = pathInfo.split( "/" );
		List< String > pathElementsAsList = new ArrayList< String >( Arrays.asList( pathElements ) );
		pathElementsAsList.removeAll( Collections.singleton( "" ) );
		
		if ( pathElementsAsList.size() != 2 )
			throw new RuntimeException( "Wrong number of elements for service name and method; relative path=" + pathInfo );
		
		return pathElementsAsList;
	}
	
	private static String getServiceRequestPayload( HttpServletRequest servletRequest )
	{
		Reader reader = __ServletRequest.getReader( servletRequest );
		Writer writer = new StringWriter();
		
		__IOUtils.copy( null, reader, writer );
		__Writer.close( writer );
		String serviceRequestPayload = writer.toString();
		
		return serviceRequestPayload;
	}
	
	public JsonServiceResponse invoke( InetSocketAddress serverAddress, BasicAuthPrincipal principal, BasicAuthCredential credential )
	{
		HttpURLConnection connection = setupConnection( serverAddress, principal, credential );
		sendRequest( connection );
		JsonServiceResponse jsonServiceResponse = new JsonServiceResponse( getResponse( connection ) );
		
		return jsonServiceResponse;
	}
	
	private HttpURLConnection setupConnection( InetSocketAddress serverAddress, BasicAuthPrincipal principal, BasicAuthCredential credential )
	{
		String credentials = principal.getName() + ":" + credential.getPassword();
		URL requestURL = __URL.__new( "http://" + serverAddress.getHostName() + ":" + serverAddress.getPort() + KelvinServlet.SERVICES_BASE + "/" + serviceName + "/" + serviceMethod );
		HttpURLConnection connection = (HttpURLConnection)__URL.openConnection( requestURL );
		__HttpURLConnection.setRequestMethod( connection, "POST" );
		connection.setDoOutput( true );
		connection.addRequestProperty( "Authorization", "Basic " + DatatypeConverter.printBase64Binary( credentials.getBytes() ) );
		connection.addRequestProperty( "Accept", "application/json" );
		connection.addRequestProperty( "Content-Type", "application/json" );
		connection.addRequestProperty( "User-Agent", "Kelvin" );
		__URLConnection.connect( connection );
		
		return connection;
	}
	
	private void sendRequest( HttpURLConnection connection )
	{
		PrintWriter printWriter = new PrintWriter( __URLConnection.getOutputStream( connection ) );
		printWriter.println( serviceRequestPayload );
		printWriter.flush();
	}
	
	private String getResponse( HttpURLConnection connection )
	{
		InputStream inputStream = __URLConnection.getInputStream( connection );
		StringWriter stringWriter = new StringWriter();
		__IOUtils.copy( null, inputStream, stringWriter );
		
		return stringWriter.toString();
	}

	public String getServiceName()
	{
		return serviceName;
	}

	public void setServiceName( String serviceName )
	{
		this.serviceName = serviceName;
	}

	public String getServiceMethod()
	{
		return serviceMethod;
	}

	public void setServiceMethod( String serviceMethod )
	{
		this.serviceMethod = serviceMethod;
	}

	public String getServiceRequestPayload()
	{
		return serviceRequestPayload;
	}

	public void setServiceRequestPayload( String serviceRequestPayload )
	{
		this.serviceRequestPayload = serviceRequestPayload;
	}
}
