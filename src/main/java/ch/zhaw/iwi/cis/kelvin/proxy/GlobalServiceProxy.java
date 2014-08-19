package ch.zhaw.iwi.cis.kelvin.proxy;

import java.net.InetSocketAddress;

import javax.security.auth.Subject;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.kelvin.restservice.GlobalRestService;
import ch.zhaw.iwi.cis.kelvin.service.GlobalService;

public class GlobalServiceProxy extends ServiceProxy implements GlobalService
{
	protected GlobalServiceProxy( InetSocketAddress serviceAddress, Subject subject )
	{
		super( serviceAddress, subject, GlobalRestService.GLOBAL_BASE );
	}

	@Override
	public String shutdown()
	{
		return getServiceTarget().path( GlobalRestService.SHUTDOWN ).request( MediaType.APPLICATION_JSON ).get().readEntity( String.class );
	}

	@Override
	public String ping()
	{
		return getServiceTarget().path( GlobalRestService.PING ).request( MediaType.APPLICATION_JSON ).get().readEntity( String.class );
	}

	@Override
	public String showPrincipal()
	{
		return getServiceTarget().path( GlobalRestService.SHOW_PRINCIPAL ).request( MediaType.APPLICATION_JSON ).get().readEntity( String.class );
	}
}
