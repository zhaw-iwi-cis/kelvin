package ch.zhaw.iwi.cis.kelvin.proxy;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import ch.zhaw.iwi.cis.kelvin.restservice.IdentifiableObjectRestService;

public abstract class ServiceProxy
{
	private WebTarget serviceTarget;
	
	@SuppressWarnings( "static-access" )
	protected ServiceProxy( String hostName, int port, String userName, String password, String servicePath )
	{
		super();
		
		HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.basic( userName, password );
		Client client = ClientBuilder.newBuilder().newClient().register( basicAuth );
		WebTarget baseTarget = client.target( "http://" + hostName + ":" + port + IdentifiableObjectRestService.SERVICES_BASE );
		serviceTarget = baseTarget.path( servicePath );
	}

	protected WebTarget getServiceTarget()
	{
		return serviceTarget;
	}
}
