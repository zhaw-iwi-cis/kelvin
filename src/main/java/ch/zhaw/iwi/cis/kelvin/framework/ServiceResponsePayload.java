package ch.zhaw.iwi.cis.kelvin.framework;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;

public class ServiceResponsePayload extends IdentifiableObject
{
	private static final long serialVersionUID = 1L;

	private Object returnValue;
	private Throwable exception;
	
	public ServiceResponsePayload()
	{
	}

	public ServiceResponsePayload( Object returnValue, Throwable exception )
	{
		super();
		this.returnValue = returnValue;
		this.exception = exception;
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
