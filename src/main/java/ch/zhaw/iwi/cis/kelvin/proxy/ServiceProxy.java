package ch.zhaw.iwi.cis.kelvin.proxy;

import java.net.InetSocketAddress;

import javax.security.auth.Subject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import ch.zhaw.iwi.cis.kelvin.framework.BasicAuthCredential;
import ch.zhaw.iwi.cis.kelvin.framework.BasicAuthPrincipal;
import ch.zhaw.iwi.cis.kelvin.restservice.IdentifiableObjectRestService;

public abstract class ServiceProxy
{
	private WebTarget serviceTarget;
	
	@SuppressWarnings( "static-access" )
	protected ServiceProxy( InetSocketAddress serviceAddress, Subject subject, String servicePath )
	{
		super();
		
		BasicAuthPrincipal principal = BasicAuthPrincipal.getBasicAuthPrincipal( subject );
		BasicAuthCredential credential = BasicAuthCredential.getBasicAuthCredential( subject );
		HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.basic( principal.getName(), credential.getPassword() );
		Client client = ClientBuilder.newBuilder().newClient().register( basicAuth );
		WebTarget baseTarget = client.target( "http://" + serviceAddress.getHostName() + ":" + serviceAddress.getPort() + IdentifiableObjectRestService.SERVICES_BASE );
		serviceTarget = baseTarget.path( servicePath );
	}

	protected WebTarget getServiceTarget()
	{
		return serviceTarget;
	}
}
