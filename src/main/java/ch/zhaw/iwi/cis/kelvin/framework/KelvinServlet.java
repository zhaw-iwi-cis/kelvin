package ch.zhaw.iwi.cis.kelvin.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class KelvinServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public static final String SERVICES_BASE = "/services";
	
	private static ThreadLocal< HttpServletRequest > servletRequest = new ThreadLocal< HttpServletRequest >();

	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		doRequest( request, response );
	}

	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		doRequest( request, response );
	}

	protected void doRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		servletRequest.set( (HttpServletRequest)request );
		
		JavaServiceResponse javaServiceResponse = null;

		try
		{
			JsonServiceRequest jsonServiceRequest = JsonServiceRequest.getJsonServiceRequest( request );
			JavaServiceRequest javaServiceRequest = JavaServiceRequest.getJavaServiceRequest( jsonServiceRequest );
			javaServiceResponse = javaServiceRequest.invoke();
		}
		catch ( Throwable t )
		{
			javaServiceResponse = new JavaServiceResponse( t );
		}

		JsonServiceResponse jsonServiceResponse = JsonServiceResponse.getJsonServiceResponse( javaServiceResponse );
		jsonServiceResponse.putJsonServiceResponse( response );
	}
	
	public static HttpServletRequest getServletRequest()
	{
		return servletRequest.get();
	}
}
