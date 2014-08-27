package ch.zhaw.iwi.cis.kelvin.test.server;

import java.net.InetSocketAddress;

import ch.zhaw.iwi.cis.kelvin.framework.ApplicationConfig;
import ch.zhaw.iwi.cis.kelvin.framework.KelvinConfig;

@ApplicationConfig
public class TestConfig extends KelvinConfig
{
	@Override
	public String getApplicationBasePackage()
	{
		return TestConfig.class.getPackage().getName();
	}

	@Override
	public InetSocketAddress getTestClientAddress()
	{
		return new InetSocketAddress( DEFAULT_TEST_CLIENT_HOSTNAME, DEFAULT_TEST_CLIENT_PORT_WITH_PROXY );
	}
}
