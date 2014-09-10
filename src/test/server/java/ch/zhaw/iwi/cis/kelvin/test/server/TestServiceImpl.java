package ch.zhaw.iwi.cis.kelvin.test.server;


import java.util.List;

import ch.zhaw.iwi.cis.kelvin.framework.service.Service;

@Service( name="TestService" )
public class TestServiceImpl implements TestService
{
	public TestClassA test( TestClassA testClassA )
	{
		return testClassA;
	}
	
	public String test2( String message )
	{
		return message;
	}

	@Override
	public List< String > test3( List< String > messages )
	{
		return messages;
	}
}
