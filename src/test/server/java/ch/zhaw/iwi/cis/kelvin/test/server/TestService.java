package ch.zhaw.iwi.cis.kelvin.test.server;

import java.util.List;

import ch.zhaw.iwi.cis.kelvin.service.Service;

public interface TestService extends Service
{
	public TestClassA test( TestClassA testClassA );
	public String test2( String message );
	public List< String > test3( List< String > messages );
}
