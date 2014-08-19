package ch.zhaw.iwi.cis.kelvin.proxy;

import java.net.InetSocketAddress;

import javax.security.auth.Subject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;
import ch.zhaw.iwi.cis.kelvin.restservice.IdentifiableObjectRestService;
import ch.zhaw.iwi.cis.kelvin.service.IdentifiableObjectService;

public abstract class IdentifiableObjectServiceProxy extends ServiceProxy implements IdentifiableObjectService
{
	protected IdentifiableObjectServiceProxy( InetSocketAddress serviceAddress, Subject subject, String servicePath )
	{
		super( serviceAddress, subject, servicePath );
	}

	public < T extends IdentifiableObject > int persist( T persistentObject )
	{
		return getServiceTarget().path( IdentifiableObjectRestService.PERSIST ).request( MediaType.APPLICATION_JSON ).post( Entity.json( persistentObject ) ).readEntity( int.class );
	}

	public < T extends IdentifiableObject > void remove( T persistentObject )
	{
		getServiceTarget().path( IdentifiableObjectRestService.REMOVE ).request( MediaType.APPLICATION_JSON ).post( Entity.json( persistentObject ) );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > T findByID( int persistentObjectID )
	{
		return (T)getServiceTarget()
			.path( IdentifiableObjectRestService.FIND_BY_ID )
			.request( MediaType.APPLICATION_JSON )
			.post( Entity.json( persistentObjectID ) )
			.readEntity( IdentifiableObject.class );
	}
}
