package ch.zhaw.iwi.cis.kelvin.restservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;
import ch.zhaw.iwi.cis.kelvin.service.GlobalService;
import ch.zhaw.iwi.cis.kelvin.service.GlobalServiceImpl;

@Path( GlobalRestService.GLOBAL_BASE )
public class GlobalRestService extends RestService
{
	public static final String GLOBAL_BASE = "/global";
	public static final String SHUTDOWN = "/shutdown";
	public static final String PING = "/ping";
	public static final String SHOW_PRINCIPAL = "/showPrincipal";
	public static final String GET_SUPPORTED_ASPECT_TYPES = "/getSupportedAspectTypes";
	public static final String FIND_ASPECTS = "/findAspects";

	private GlobalService globalService = ServiceRegistry.getRegistry().getService( GlobalServiceImpl.class.getSimpleName() );
	
	@GET
    @Path( SHUTDOWN )
	public String shutdown()
	{
		return globalService.shutdown();
	}

	@GET
	@Path( PING )
	public String ping()
	{
		return globalService.ping();
	}

	@GET
	@Path( SHOW_PRINCIPAL )
	public String showPrincipal()
	{
		return globalService.showPrincipal();
	}
}
