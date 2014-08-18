package ch.zhaw.iwi.cis.kelvin.framework;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

@Provider
public class CustomObjectMapperProviderServer implements ContextResolver< ObjectMapper >
{
	private final ObjectMapper mapper;

	public CustomObjectMapperProviderServer()
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
		result.enableDefaultTyping( DefaultTyping.JAVA_LANG_OBJECT );
		result.registerModule( new GuavaModule() );
		return result;
	}
}
