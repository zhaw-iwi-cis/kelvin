package ch.zhaw.iwi.cis.kelvin.test.client;

import org.junit.Test;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinConfig;
import ch.zhaw.iwi.cis.kelvin.proxy.ServiceProxyManager;
import ch.zhaw.iwi.cis.kelvin.service.GlobalService;

public class BasicTest
{
	@Test
	public void test()
	{
		KelvinConfig config = KelvinConfig.getConfig();
		ServiceProxyManager manager = ServiceProxyManager.getServiceProxyManager( config.getTestClientAddress(), config.getTestClientSubject() );
		GlobalService globalService = manager.createServiceProxy( GlobalService.class );
		globalService.ping();
	}
}
