package ch.zhaw.iwi.cis.kelvin.framework;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import __javax.servlet.__ServletResponse;

public class JsonServiceResponse extends ServiceRequest
{
	private String serviceReturnValueOrException;

	public JsonServiceResponse( String serviceReturnValueOrException )
	{
		super();
		this.serviceReturnValueOrException = serviceReturnValueOrException;
	}
	
	public static JsonServiceResponse getJsonServiceResponse( JavaServiceResponse javaServiceResponse )
	{
		Object[] retValOrException = new Object[] {
			javaServiceResponse.getRetVal(),
			javaServiceResponse.getException()
		};
		
		return new JsonServiceResponse( writeValueAsString( retValOrException ) );
	}

	public void putJsonServiceResponse( HttpServletResponse response )
	{
		response.addHeader( "Content-Type", "application/json" );
		PrintWriter writer = __ServletResponse.getWriter( response );
		writer.println( serviceReturnValueOrException );
		writer.flush();
	}

	public String getServiceReturnValueOrException()
	{
		return serviceReturnValueOrException;
	}

	public void setServiceReturnValueOrException( String serviceReturnValueOrException )
	{
		this.serviceReturnValueOrException = serviceReturnValueOrException;
	}
}
