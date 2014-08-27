package ch.zhaw.iwi.cis.kelvin.framework;

public class JavaServiceResponse extends ServiceRequest
{
	private Object retVal;
	private Throwable exception;
	
	public JavaServiceResponse( Object retVal )
	{
		super();
		this.retVal = retVal;
	}
	
	public JavaServiceResponse( Throwable exception )
	{
		super();
		this.exception = exception;
	}
	
	public JavaServiceResponse( Object[] retValOrException )
	{
		super();
		this.retVal = retValOrException[ 0 ];
		this.exception = (Throwable)retValOrException[ 1 ];
	}
	
	public static JavaServiceResponse getJavaServiceResponse( JsonServiceResponse jsonServiceResponse )
	{
		Object[] retValOrException = readValue( jsonServiceResponse.getServiceReturnValueOrException(), Object[].class );
		JavaServiceResponse response = new JavaServiceResponse( retValOrException );
		
		return response;
	}

	public Object getRetVal()
	{
		return retVal;
	}

	public void setRetVal( Object retVal )
	{
		this.retVal = retVal;
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
