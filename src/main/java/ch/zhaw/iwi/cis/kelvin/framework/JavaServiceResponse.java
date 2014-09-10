package ch.zhaw.iwi.cis.kelvin.framework;


public class JavaServiceResponse extends ServiceRequest
{
	private Object returnValue;
	private Throwable exception;
	
	public JavaServiceResponse( Object returnValue )
	{
		this( returnValue, null );
	}
	
	public JavaServiceResponse( Throwable exception )
	{
		this( null, exception );
	}
	
	public JavaServiceResponse( Object returnValue, Throwable exception )
	{
		super();
		this.returnValue = returnValue;
		this.exception = exception;
	}
	
	public static JavaServiceResponse getJavaServiceResponse( JsonServiceResponse jsonServiceResponse )
	{
		ServiceResponsePayload serviceResponsePayload = readValue( jsonServiceResponse.getServiceReturnValueOrException(), ServiceResponsePayload.class );
		JavaServiceResponse response = new JavaServiceResponse( serviceResponsePayload.getReturnValue(), serviceResponsePayload.getException() );
		
		return response;
	}

	public Object getReturnValue()
	{
		return returnValue;
	}

	public void setReturnValue( Object returnValue )
	{
		this.returnValue = returnValue;
	}

	public Throwable getException()
	{
		return exception;
	}

	public void setException( Throwable exception )
	{
		this.exception = exception;
	}
}
