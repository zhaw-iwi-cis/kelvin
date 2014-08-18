package ch.zhaw.iwi.cis.kelvin.framework;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

@Provider
public class CustomObjectMapperProviderClient implements ContextResolver< ObjectMapper >
{
	private final ObjectMapper mapper;

	public CustomObjectMapperProviderClient()
	{
		mapper = createCustomMapper();
	}

	@Override
	public ObjectMapper getContext( Class< ? > type )
	{
		return mapper;
	}

	private static ObjectMapper createCustomMapper()
	{
		final ObjectMapper result = new ObjectMapper();
		result.enableDefaultTyping();
		result.registerModule( new GuavaModule() );
		return result;
	}
}
