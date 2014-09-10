package ch.zhaw.iwi.cis.kelvin.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;

public class ServiceRequestPayload extends IdentifiableObject
{
	private static final long serialVersionUID = 1L;

	private List< Object > serviceArgs = new ArrayList< Object >();
	
	public ServiceRequestPayload()
	{
		this( new Object[] {} );
	}

	public ServiceRequestPayload( Object ... serviceArgs )
	{
		super();
		this.serviceArgs = Arrays.asList( serviceArgs );
	}

	public List< Object > getServiceArgs()
	{
		return serviceArgs;
	}

	public void setServiceArgs( List< Object > serviceArgs )
	{
		this.serviceArgs = serviceArgs;
	}
}
