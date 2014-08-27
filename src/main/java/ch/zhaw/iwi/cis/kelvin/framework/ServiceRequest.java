package ch.zhaw.iwi.cis.kelvin.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ServiceRequest
{
	// Note that ObjectMapper is (apparently) thread-safe
	// (according to quick google; couldn't verify in javadocs).
	private static ObjectMapper mapper = new ObjectMapper();
	
	static
	{
		mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
		mapper.configure( SerializationFeature.INDENT_OUTPUT, true );
		mapper.enableDefaultTypingAsProperty( ObjectMapper.DefaultTyping.NON_FINAL, "class" );
	}

	// TODO Fix probable bug in deflector that prevents this method from being generated.
	public static < T > T readValue( String value, Class< T > valueType )
	{
		try
		{
			return mapper.readValue( value, valueType );
		}
		catch ( Throwable e )
		{
			throw new RuntimeException( e );
		}
	}

	public static String writeValueAsString( Object value )
	{
		try
		{
			return mapper.writeValueAsString( value );
		}
		catch ( Throwable e )
		{
			throw new RuntimeException( e );
		}
	}
}
