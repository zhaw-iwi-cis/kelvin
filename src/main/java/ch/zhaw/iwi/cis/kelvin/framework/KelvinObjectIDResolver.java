package ch.zhaw.iwi.cis.kelvin.framework;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

public class KelvinObjectIDResolver implements ObjectIdResolver
{
	private Map< IdKey, Object > idToObjectMap = new HashMap< IdKey, Object >();

	@Override
	public void bindItem( IdKey id, Object pojo )
	{
		idToObjectMap.put( id, pojo );
	}

	@Override
	public Object resolveId( IdKey id )
	{
		return idToObjectMap.get( id );
	}

	@Override
	public ObjectIdResolver newForDeserialization( Object context )
	{
		return this;
	}

	@Override
	public boolean canUseFor( ObjectIdResolver resolverType )
	{
		return resolverType.getClass() == getClass();
	}
}
