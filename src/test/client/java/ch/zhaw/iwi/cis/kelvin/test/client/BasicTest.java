package ch.zhaw.iwi.cis.kelvin.test.client;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinConfig;
import ch.zhaw.iwi.cis.kelvin.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.kelvin.service.GlobalService;
import ch.zhaw.iwi.cis.kelvin.test.server.TestClassA;
import ch.zhaw.iwi.cis.kelvin.test.server.TestClassAService;
import ch.zhaw.iwi.cis.kelvin.test.server.TestClassB;
import ch.zhaw.iwi.cis.kelvin.test.server.TestService;

public class BasicTest
{
	@Test
	public void test()
	{
		KelvinConfig config = KelvinConfig.getConfig();
		ServiceProxyManager manager = ServiceProxyManager.getServiceProxyManager(
			config.getTestClientAddress(),
			config.getTestClientSubject() );
		GlobalService globalService = manager.createServiceProxy( GlobalService.class );
		globalService.ping();
	}

	@Test
	public void test2()
	{
		KelvinConfig config = KelvinConfig.getConfig();
		ServiceProxyManager manager = ServiceProxyManager.getServiceProxyManager(
			config.getTestClientAddress(),
			config.getTestClientSubject() );
		TestService testService = manager.createServiceProxy( TestService.class );
		testService.test( new TestClassA( "value1", new TestClassB( "value2" ), new TestClassB( "value3" ) ) );
		testService.test2( "testing" );
		testService.test3( Arrays.asList( new String[] { "value1", "value2" } ) );
	}

	@Test
	public void testPersist()
	{
		KelvinConfig config = KelvinConfig.getConfig();
		ServiceProxyManager manager = ServiceProxyManager.getServiceProxyManager(
			config.getTestClientAddress(),
			config.getTestClientSubject() );
		TestClassAService testService = manager.createServiceProxy( TestClassAService.class );

		TestClassA tcA = new TestClassA( "value1" );
		testService.persist( tcA );
		TestClassA tc2A = new TestClassA( "value2" );
		testService.persist( tc2A );
		TestClassA tc3A = new TestClassA( "value3" );
		testService.persist( tc3A );

	}

	@Test
	public void testFindByAll()
	{
		KelvinConfig config = KelvinConfig.getConfig();
		ServiceProxyManager manager = ServiceProxyManager.getServiceProxyManager(
			config.getTestClientAddress(),
			config.getTestClientSubject() );
		TestClassAService testService = manager.createServiceProxy( TestClassAService.class );

		List< TestClassA > l = testService.findByAll( TestClassA.class.getName() );
		for ( TestClassA tca : l )
		{
			System.out.println( tca.getClass() );
		}

	}

	@Test
	public void test3()
	{
		KelvinConfig config = KelvinConfig.getConfig();
		ServiceProxyManager manager = ServiceProxyManager.getServiceProxyManager(
			config.getTestClientAddress(),
			config.getTestClientSubject() );
		TestClassAService testService = manager.createServiceProxy( TestClassAService.class );

		testService.findByAll( TestClassA.class.getName() );

	}
}
