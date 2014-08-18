package ch.zhaw.iwi.cis.kelvin.restservice;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;
import ch.zhaw.iwi.cis.kelvin.service.IdentifiableObjectService;

public abstract class IdentifiableObjectRestService extends RestService
{
	public static final String PERSIST = "/persist";
	public static final String REMOVE = "/remove";
	public static final String FIND_BY_ID = "/findByID";
	
	public < T extends IdentifiableObject > int persist( T persistentObject )
	{
		return getPersistentObjectService().persist( persistentObject );
	}

	public < T extends IdentifiableObject > void remove( T persistentObject )
	{
		getPersistentObjectService().remove( persistentObject );
	}

	public < T extends IdentifiableObject > T findByID( int id )
	{
		return getPersistentObjectService().findByID( id );
	}
	
	protected abstract IdentifiableObjectService getPersistentObjectService();
}
